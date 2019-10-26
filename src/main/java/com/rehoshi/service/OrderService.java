package com.rehoshi.service;

import com.rehoshi.dto.search.OrderPageSearch;
import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.model.Order;

import java.util.List;

/**
 * 订单业务
 */
@Deprecated
public interface OrderService {

    /**
     * 新增订单
     * 订单状态 为待发货
     * @param order 需要发货的订单信息
     * @return 返回订单id
     */
    RespData<String> save (Order order) ;

    /**
     * 按照id 修改订单信息
     * @param order 需要修改的订单信息
     * @return
     */
    RespData<Boolean> update(Order order) ;

    /**
     * 发送订单
     * 修改订单状态 为已发货
     * @param id 需要发送的订单id
     * @return
     */
    RespData<Boolean> send(String id) ;

    /**
     * 删除订单
     * @param id 需要删除的订单id
     * @return
     */
    RespData<Boolean> deleteById(String id) ;

    /**
     * 批量删除订单
     * @param ids 需要删除的订单id
     * @return
     */
    RespData<Boolean> deleteInIds(List<String> ids) ;

    /**
     * 订单分页
     * @param search 查询信息
     * @param pageIndex 页码
     * @param pageSize 每页数据量
     * @return
     */
    PageData<Order> orderInPage(OrderPageSearch search, int pageIndex, int pageSize) ;

    /**
     * 订单列表
     * @param search 查询信息
     * @return
     */
    RespData<List<Order>> orderList(OrderPageSearch search) ;

    /**
     * 根据id 获取订单
     * @param id 订单id
     * @return
     */
    RespData<Order> getById(String id) ;

}
