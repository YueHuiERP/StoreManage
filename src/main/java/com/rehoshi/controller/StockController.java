package com.rehoshi.controller;

import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.StockPageSearch;
import com.rehoshi.model.Stock;
import com.rehoshi.model.Supplier;
import com.rehoshi.service.StockService;
import com.rehoshi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    /**
     * 查询所有库存  返回分页数据
     *
     * @return
     */
    @RequestMapping(value = "/stockInPage", method = RequestMethod.GET)
    public PageData<Stock> stockInPage(
            @RequestParam(required = false) String name,
            @RequestParam(value = "startTime", required = false) String startTimeStr,
            @RequestParam(value = "endTime", required = false) String endTimeStr,
            @RequestParam("page") Integer page,
            @RequestParam("limit") Integer limit,
            @RequestParam(value = "minRemain", required = false) Double minRemain,
            @RequestParam(value = "goodsType", required = false) Integer goodsType
    ) {
        Date startTime = DateUtil.toDate(startTimeStr);
        Date endTime = DateUtil.toDate(endTimeStr);


        if (endTime != null) {
            endTime = DateUtil.addTime(endTime, 1, DateUtil.Unit.DAY);
        }

        StockPageSearch search = new StockPageSearch();
        search.setName(name);
        search.setStartTime(startTime);
        search.setEndTime(endTime);
        search.setMinRemain(minRemain);
        search.setGoodsType(goodsType);
        return stockService.stockInPage(search, page, limit);

    }

    /**
     * 删除库存
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delStock/{id}", method = RequestMethod.DELETE)
    public RespData<Boolean> delStock(@PathVariable String id) {
        return stockService.deleteById(id);
    }


    /**
     * 添加库存
     *
     * @param stock
     * @return
     */
    @RequestMapping(value = "/addStock", method = RequestMethod.POST)
    public RespData<Boolean> addStock(@RequestBody Stock stock) {
        return stockService.addStock(stock);
    }


    /**
     * 批量删除库存
     *
     * @param stockList
     * @return
     */
    @RequestMapping(value = "/delBatchStock", method = RequestMethod.DELETE)
    public RespData<Boolean> delBatchStock(@RequestBody List<Stock> stockList) {

        return stockService.delBatchStock(stockList);
    }

    /**
     * 更新库存
     *
     * @param stock
     * @return
     */
    @RequestMapping(value = "/editStock", method = RequestMethod.PUT)
    public RespData<Boolean> editStock(@RequestBody Stock stock) {
        return stockService.editStock(stock);
    }

    @PostMapping(value = "/add/all")
    public RespData<Boolean> batchAdd(@RequestBody List<Stock> stockList) {
        return stockService.batchSave(stockList);
    }

    /**
     * 从不同的供应商添加库存
     * @param stock
     * @return
     */
    @PostMapping(value = "add/suppliers")
    public RespData<Boolean> addFromSuppliers(@RequestBody Stock stock){
        return stockService.addFromSuppliers(stock);
    }

}
