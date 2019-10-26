package com.rehoshi.service;

import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.SupplierPageSearch;
import com.rehoshi.model.Supplier;

import java.util.List;

/**
 * 供应商业务
 */
public interface SupplierService {

    /**
     * 分页查询供应商列表
     */
    PageData<Supplier> listInPage(SupplierPageSearch search) ;

    /**
     *根据id 查询供应商
     */
    RespData<Supplier> getById(String id) ;

    /**
     * 保存供应商信息
     * @return 返回供应商id
     */
    RespData<String> save(Supplier supplier) ;

    /**
     * 根据id 更新供应商id
     * @return 返回更新成功失败
     */
    RespData<Boolean> update(Supplier supplier) ;

    /**
     * 根据id 删除 供应商
     */
    RespData<Boolean> deleteById(String id) ;

    /**
     * 批量删除供应商
     */
    RespData<Boolean> deleteInIds(List<String> ids) ;
}
