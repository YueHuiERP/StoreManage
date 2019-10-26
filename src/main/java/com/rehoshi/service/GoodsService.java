package com.rehoshi.service;

import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.GoodPageSearch;
import com.rehoshi.model.Goods;

import java.util.List;

/**
 * 原品业务
 */
public interface GoodsService {

    /**
     * 新增一个原品
     * @param goods 原品数据
     * @return 成功返回订单id  失败返回false
     */
    RespData<String> save(Goods goods) ;

    /**
     * 根据id更新一个原品
     * @param goods  原品数据
     * @return 成功返回true  失败 返回false
     */
    RespData<Boolean> update(Goods goods) ;

    /**
     * 根据id 删除一个原品
     * @param id 原品 id
     * @return 成功返回true  失败返回false
     */
    RespData<Boolean> deleteById(String id) ;

    /**
     * 获取所有商品
     * 按照type = Goods 查找
     * @return
     */
    RespData<List<Goods>> getAllGoods() ;

    /**
     * 获取所有材料
     * 按照type = Material 查找
     * @return
     */
    RespData<List<Goods>> getAllMaterial() ;

    /**
     * 获取所有包材
     * 按照type = PackageMaterial 查找
     * @return
     */
    RespData<List<Goods>> getAllPackageMaterial() ;

    /**
     * 原品分页列表
     * @param search 需要查找的关键字
     * @param pageIndex 页码
     * @param pageSize 每页数据量
     * @return
     */
    PageData<Goods> goodsInPage(GoodPageSearch search, int pageIndex, int pageSize) ;

    /**
     * 根据id 获取原品
     * @param id 原品id
     * @return
     */
    RespData<Goods> getById(String id);

    RespData<Goods> queryGoodById(String id);

    RespData addGoodsType(Goods goods);

    RespData delGoodsType(String id);

    RespData<Boolean> editGoods(Goods good);

    RespData<Boolean> delBatchGoodTypes(List<Goods> goodslist);
}
