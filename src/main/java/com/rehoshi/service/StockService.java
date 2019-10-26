package com.rehoshi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.StockPageSearch;
import com.rehoshi.model.Stock;

import java.util.List;

public interface StockService extends IService<Stock>  {

    /**
     * 库存的分页信息
     * @param search 查找关键字
     * @param pageIndex 页码
     * @param pageSize 每页数据量
     * @return
     */
    PageData<Stock> stockInPage(StockPageSearch search, int pageIndex, int pageSize) ;

    /**
     * 根据id 删除库存信息
     * @param id 需要删除的库存id
     * @return 成功返回true  失败返回 false
     */
    RespData<Boolean> deleteById(String id) ;

    RespData<Boolean> addStock(Stock stock);

    RespData<Boolean> delBatchStock(List<Stock> stockList);

    RespData<Boolean> editStock(Stock stock);

    RespData<List<Stock>> list(StockPageSearch search);

    /**
     * 批量插入库存
     * @param stockList
     * @return
     */
    RespData<Boolean> batchSave(List<Stock> stockList);

    /**
     * 从不同的供应商处添加库存
     * @param stock
     * @return
     */
    RespData<Boolean> addFromSuppliers(Stock stock);
}
