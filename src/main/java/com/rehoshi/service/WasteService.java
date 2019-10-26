package com.rehoshi.service;

import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.WastePageSearch;
import com.rehoshi.model.Waste;

import java.util.List;

/**
 * 损耗业务
 */
public interface WasteService {

    /**
     * 新增损耗
     * @param waste  需要保存损耗信息
     * @return 返回损耗id
     */
    RespData<String> save(Waste waste) ;

    /**
     * 根据id 修改损耗
     * @param waste 损耗信息
     * @return 成功返回true  失败返回false
     */
    RespData<Boolean> update(Waste waste) ;

    /**
     * 通过id 获取损耗信息
     * @param id 损耗id
     * @return
     */
    RespData<Waste> getById(String id) ;

    /**
     * 通过id删除损耗
     * @param id 需要删除的损耗id
     * @return
     */
    RespData<Boolean> deleteById(String id) ;

    /**
     * 损耗分页列表
     * @param search 查询内容
     * @param pageIndex 页码
     * @param pageSize 每页数据量
     * @return 损耗分页数据
     */
    PageData<Waste> wasteInPage(WastePageSearch search, int pageIndex, int pageSize) ;

    /**
     * 批量删除id
     * @param ids
     * @return
     */
    RespData<Boolean> deleteInIds(List<String> ids);

    RespData<List<Waste>> list(WastePageSearch search);

}
