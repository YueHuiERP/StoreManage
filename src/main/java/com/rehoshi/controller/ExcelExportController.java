package com.rehoshi.controller;

import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.excel.*;
import com.rehoshi.dto.excel.adapt.ExcelMergeHelper;
import com.rehoshi.dto.excel.adapt.TableInfo;
import com.rehoshi.dto.search.*;
import com.rehoshi.model.*;
import com.rehoshi.service.*;
import com.rehoshi.util.CollectionUtil;
import com.rehoshi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("excel/export")
public class ExcelExportController extends BaseExcelController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private WasteService wasteService;

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductService productService;


    @Autowired
    private ManifestService manifestService;

    @GetMapping("stock")
    public void stock(@RequestParam(required = false) String name,
                      @RequestParam(value = "startTime", required = false) String startTimeStr,
                      @RequestParam(value = "endTime", required = false) String endTimeStr) {
        Date startTime = DateUtil.toDate(startTimeStr);
        Date endTime = DateUtil.toDate(endTimeStr);

        if (endTime != null) {
            endTime = DateUtil.addTime(endTime, 1, DateUtil.Unit.DAY);
        }

        StockPageSearch search = new StockPageSearch();
        search.setName(name);
        search.setStartTime(startTime);
        search.setEndTime(endTime);
        search.setStockType(Stock.Type.CHILD);
        RespData<List<Stock>> listRespData = stockService.list(search);
        export(StockRow.class, CollectionUtil.map(listRespData.data, StockRow::new), "库存");
    }

    @GetMapping("product/waste")
    public void productWaste(@RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "startTime", required = false) String startTimeStr,
                             @RequestParam(value = "endTime", required = false) String endTimeStr) {
        Date startDate = DateUtil.toDate(startTimeStr);
        Date endDate = DateUtil.toDate(endTimeStr);

        if (endDate != null) {
            endDate = DateUtil.endOfDay(endDate);
        }
        WastePageSearch search = new WastePageSearch();
        search.setStartTime(startDate);
        search.setEndTime(endDate);
        search.setName(name);
        RespData<List<Waste>> list = wasteService.list(search);
        export(WasteRow.class, CollectionUtil.map(list.data, WasteRow::new), "生产-损耗");
    }

    @GetMapping("product")
    public void product(@RequestParam(required = false) String name,
                        @RequestParam(value = "startTime", required = false) String startTimeStr,
                        @RequestParam(value = "endTime", required = false) String endTimeStr) {
        ProductPageSearch search = new ProductPageSearch();
        search.setName(name);
        search.setStartTime(DateUtil.toDate(startTimeStr));
        search.setEndTime(DateUtil.addTime(DateUtil.toDate(endTimeStr), 1, DateUtil.Unit.DAY));
        RespData<List<Product>> listRespData = productService.list(search);
        TableInfo table = ProductTable.createTable(listRespData.data);
        export(table, "生产-生产");
    }

    @GetMapping("finance")
    public void finance(@RequestParam(value = "startTime", required = false) String startTimeStr,
                        @RequestParam(value = "endTime", required = false) String endTimeStr) {
        Date startDate = DateUtil.toDate(startTimeStr);

        Date endDate = DateUtil.toDate(endTimeStr);

        if (endDate != null) {
            endDate = DateUtil.endOfDay(endDate);
        }
        StockPageSearch search = new StockPageSearch();
        search.setStartTime(startDate);
        search.setEndTime(endDate);
        search.setGoodsType(Goods.Type.GOODS);
        RespData<List<Stock>> list = stockService.list(search);
        List<FinanceRow> rowList = new ArrayList<>();
        List<ExcelMergeInfo> mergeInfos = new ArrayList<>();
        CollectionUtil.foreach(list.data, data -> {
            //查找到对应的子库存
            RespData<List<Stock>> children = stockService.getByParentId(data.getId());
            CollectionUtil.foreach(children.data, stock->{
                stock.setParent(data);
            });
            //查找使用该库存生产的产品
            RespData<List<Product>> productList = productService.getByStockId(data.getId());
            if (children.data == null) {
                children.data = new ArrayList<>();
            }
            if (productList.data == null) {
                productList.data = new ArrayList<>();
            }

            int startRow = rowList.size() + 1;
            //对齐两个列表长度  补null
            CollectionUtil.alignCount(children.data, productList.data, index -> null, index -> null);
            //创建导出Excel对象
            CollectionUtil.foreach(children.data, productList.data, (stock, product) -> {
                rowList.add(new FinanceRow(stock, product));
            });
            //计算合并信息
            int endRow = rowList.size();
            if (endRow - startRow > 0) {
                for (int i = 6; i < 9; i++) {
                    ExcelMergeInfo mergeInfo = new ExcelMergeInfo();
                    mergeInfo.setStartCol(i);
                    mergeInfo.setEndCol(i);
                    mergeInfo.setStartRow(startRow);
                    mergeInfo.setEndRow(endRow);
                    mergeInfos.add(mergeInfo);
                }
            }
        });
        export(FinanceRow.class, rowList, "财务-成本统计", mergeInfos);
    }


    @GetMapping("order")
    public void orderList(@RequestParam(value = "startTime", required = false) String startTime,
                          @RequestParam(value = "endTime", required = false) String endTime,
                          @RequestParam(value = "status", required = false) Integer status,
                          @RequestParam(value = "name", required = false) String name, HttpServletResponse response) {
        Date startDate = DateUtil.toDate(startTime);

        Date endDate = DateUtil.toDate(endTime);

        if (endDate != null) {
            endDate = DateUtil.addTime(endDate, 1, DateUtil.Unit.DAY);
        }
        OrderPageSearch search = new OrderPageSearch();
        search.setName(name);
        search.setStartTime(startDate);
        search.setEndTime(endDate);
        search.setStatus(status);
        RespData<List<Order>> listRespData = orderService.orderList(search);
        String statusStr = "";
        if (status == null || status == Order.Status.WAIT_SEND) {
            statusStr = "待发货";
            List<PrepareOrderRow> prepareOrderRows = new ArrayList<>();
            List<ExcelMergeInfo> mergeInfos = ExcelMergeHelper.merge(3, listRespData.data, prepareOrderRows, new ExcelMergeHelper.Adapter<Order, ProductComposition, PrepareOrderRow>() {

                @Override
                public List<ProductComposition> adapt(Order model) {
                    return model.getItems();
                }

                @Override
                public PrepareOrderRow createRow(Order model, ProductComposition subModel) {
                    return new PrepareOrderRow(model, subModel);
                }
            });
            export(PrepareOrderRow.class, prepareOrderRows, "订单-" + statusStr, mergeInfos);
        } else {
            statusStr = "已发货";
            List<SendOrderRow> sendOrderRows = new ArrayList<>();
            List<ExcelMergeInfo> mergeInfos = null;
            mergeInfos = ExcelMergeHelper.merge(3, listRespData.data, sendOrderRows, new ExcelMergeHelper.Adapter<Order, Order, SendOrderRow>() {
                @Override
                public List<Order> adapt(Order model) {
                    return model.getSubOrders();
                }

                @Override
                public SendOrderRow createRow(Order model, Order subModel) {
                    return new SendOrderRow(model, subModel);
                }
            });
            export(SendOrderRow.class, sendOrderRows, "订单-" + statusStr, mergeInfos);
        }


    }

    @GetMapping("manifest")
    public void manifestList(@RequestParam(value = "startTime", required = false) String startTime,
                             @RequestParam(value = "endTime", required = false) String endTime,
                             @RequestParam(value = "status", required = false) Integer status,
                             @RequestParam(value = "name", required = false) String name) {
        Date startDate = DateUtil.toDate(startTime);

        Date endDate = DateUtil.toDate(endTime);

        if (endDate != null) {
            endDate = DateUtil.endOfDay(endDate);
        }
        ManifestPageSearch search = new ManifestPageSearch();
        search.setName(name);
        search.setStartTime(startDate);
        search.setEndTime(endDate);
        search.setStatus(status);
        PageData<Manifest> manifestPageData = manifestService.listInPage(search);
        String statusStr = "";
        if (status == null || status == Order.Status.WAIT_SEND) {
            statusStr = "待发货";
            export(PrepareManifestRow.class, CollectionUtil.map(manifestPageData.data, PrepareManifestRow::new), "订单-" + statusStr);
        } else {
            statusStr = "已发货";
            export(SendManifestRow.class, CollectionUtil.map(manifestPageData.data, SendManifestRow::new), "订单-" + statusStr);
        }
    }
}
