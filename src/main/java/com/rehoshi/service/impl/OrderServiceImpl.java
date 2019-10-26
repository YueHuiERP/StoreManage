package com.rehoshi.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rehoshi.dao.OrderMapper;
import com.rehoshi.dao.ProductCompositionMapper;
import com.rehoshi.dao.ProductMapper;
import com.rehoshi.dao.StatisticsMapper;
import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.OrderPageSearch;
import com.rehoshi.model.Order;
import com.rehoshi.model.ProductComposition;
import com.rehoshi.service.OrderService;
import com.rehoshi.util.CollectionUtil;
import com.rehoshi.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
@Deprecated
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private ProductCompositionMapper productCompositionMapper;

    @Resource
    private StatisticsMapper statisticsMapper;

    @Resource
    private ProductMapper productMapper ;

    @Override
    public RespData<String> save(Order order) {
        RespData<String> data = RespData.fail("").setMsg("添加订单失败");
        if (order == null) {
            data.setMsg("请输入订单信息");
        } else if (order.getStatus() == Order.Status.WAIT_SEND && CollectionUtil.isNullOrEmpty(order.getItems())) {
            data.setMsg("请选择商品");
        } else if (order.getStatus() == Order.Status.HAS_SENT && CollectionUtil.isNullOrEmpty(order.getSubOrders())) {
            data.setMsg("请选择成品");
        } else {
            order.newId();
            order.setCreateTime(new Date());

            saveOrderRelation(order);

            int save = orderMapper.save(order);
            if (save > 0) {
                data.success().setData(order.getId()).setMsg("添加订单成功");
            }
        }
        return data;
    }

    private void saveOrderRelation(Order order) {
        if (order.getStatus() == Order.Status.WAIT_SEND) {
            order.newItems();
            if (!CollectionUtil.isNullOrEmpty(order.getItems())) {
                productCompositionMapper.save(order.getItems());
            }
        } else if (order.getStatus() == Order.Status.HAS_SENT) {
            order.newChildren();
            if (!CollectionUtil.isNullOrEmpty(order.getSubOrders())) {
                orderMapper.saveAll(order.getSubOrders());
            }
        }
    }

    @Override
    public RespData<Boolean> update(Order order) {
        RespData<Boolean> data = RespData.fail(false).setMsg("更新失败");
        if (DateUtil.getDiff(order.getCreateTime()) < 0) {
            data.setMsg("创建时间不能超过当前时间");
        } else if (order.getStatus() == Order.Status.WAIT_SEND && CollectionUtil.isNullOrEmpty(order.getItems())) {
            data.setMsg("请选择商品");
        } else if (order.getStatus() == Order.Status.HAS_SENT && CollectionUtil.isNullOrEmpty(order.getSubOrders())) {
            data.setMsg("请选择成品");
        } else {
            //先删除之前的关系
            if (order.getStatus() == Order.Status.WAIT_SEND) {
                productCompositionMapper.deleteByOrderId(order.getId());
            } else {
                orderMapper.deleteByParentId(order.getId());
            }

            int update = orderMapper.update(order);
            if (update > 0) {

                //重新保存关系
                saveOrderRelation(order);

                data.success().setData(true).setMsg("更新成功");
            }
        }
        return data;
    }

    @Override
    public RespData<Boolean> send(String id) {
        return null;
    }

    @Override
    public RespData<Boolean> deleteById(String id) {
        RespData<Boolean> data = RespData.fail(false).setMsg("删除订单失败");
        int i = orderMapper.deleteById(id);
        if (i > 0) {
            //删除关系
            orderMapper.deleteByParentId(id);
            productCompositionMapper.deleteByOrderId(id);
            data.success().setData(true).setMsg("删除订单成功");
        }
        return data;
    }

    @Override
    public RespData<Boolean> deleteInIds(List<String> ids) {
        RespData<Boolean> data = RespData.fail(false).setMsg("批量删除订单失败");

        if (ids != null && !ids.isEmpty()) {
            int i = orderMapper.deleteInIds(ids);
            if (i > 0) {
                //批量删除子订单关系
                orderMapper.deleteInParentIds(ids);
                productCompositionMapper.deleteByOrderIdInIds(ids);
                data.success().setData(true).setMsg("批量删除订单成功");
            }
        } else {
            data.setMsg("至少选择一条订单删除");
        }
        return data;
    }

    @Override
    public PageData<Order> orderInPage(OrderPageSearch search, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Order> bySearch = orderMapper.getBySearch(search);
        return new PageData<>(new PageInfo<>(bySearch));
    }

    @Override
    public RespData<List<Order>> orderList(OrderPageSearch search) {
        List<Order> bySearch = orderMapper.getBySearch(search);
        assembleOrderList(bySearch);
        return RespData.success(bySearch );
    }

    private void assembleOrderList(List<Order> orderList){
        CollectionUtil.foreach(orderList, this::assembleOrder);
    }

    private void assembleOrder(Order order){
        order.setSubOrders(orderMapper.getByParentId(order.getId()));
        order.setItems(productCompositionMapper.getByOrderId(order.getId()));
        CollectionUtil.foreach(order.getItems(), item -> {
//            item.setSendAmount(statisticsMapper.getOrderItemSendAmount(item.getId()));
            item.setSendAmount(getOrderItemSendAmount(item));
        });
    }

    @Override
    public RespData<Order> getById(String id) {
        RespData<Order> data = new RespData<Order>().setData(null).setMsg("查询订单失败");
        Order byId = orderMapper.getById(id);
        if (byId != null) {
            assembleOrder(byId);
            data.success().setData(byId).setMsg("查询订单成功");
        }
        return data;
    }

    //获取订单的原料发货量
    private Double getOrderItemSendAmount(ProductComposition item){
        item.setgId(item.getGoods().getId());
        AtomicReference<Double> sendAmount = new AtomicReference<>(0d);
        //获取该订单下的所有发货订单
        List<Order> byParentId = orderMapper.getByParentId(item.getoId());
        CollectionUtil.foreach(byParentId, data -> {
            //获取发货条目
            List<Order> sendItems = orderMapper.getByParentId(data.getId());
            CollectionUtil.foreach(sendItems, sdIt ->{
                sdIt.setPId(sdIt.getProduct().getId());
                //获取
                List<ProductComposition> byProductIdAndGid = productCompositionMapper.getByProductIdAndGid(sdIt.getPId(), item.getgId());

                CollectionUtil.foreach(byProductIdAndGid, cops ->{
                    Double specsValue = cops.getSpecsValue();
                    Integer amount = sdIt.getAmount();
                    sendAmount.updateAndGet(v -> v + amount * specsValue);
                });

            });
        });
        Double aDouble = sendAmount.get();
        return aDouble;
    }
}
