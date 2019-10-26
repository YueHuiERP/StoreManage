package com.rehoshi.service;

import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.ManifestPageSearch;
import com.rehoshi.dto.search.OrderPageSearch;
import com.rehoshi.model.Manifest;
import com.rehoshi.model.Order;

import java.util.List;

public interface ManifestService {
    /**
     * 新增订单
     * 订单状态 为待发货
     * @param manifest 需要发货的订单信息
     * @return 返回订单id
     */
    RespData<String> save (Manifest manifest) ;

    /**
     * 按照id 修改订单信息
     * @param manifest 需要修改的订单信息
     * @return
     */
    RespData<Boolean> update(Manifest manifest) ;

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
     * @param search 分页查询信息
     * @return
     */
    PageData<Manifest> listInPage(ManifestPageSearch search) ;

    /**
     * 根据id 获取订单
     * @param id 订单id
     * @return
     */
    RespData<Manifest> getById(String id) ;

    RespData<List<Manifest>> batchPrepareListByGid(String gId);

    /**
     * 批量插入
     * @param manifestList
     * @return
     */
    RespData<Boolean> save(List<Manifest> manifestList);
}
