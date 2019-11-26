package com.rehoshi.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rehoshi.dao.*;
import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.GoodPageSearch;
import com.rehoshi.model.BaseModel;
import com.rehoshi.model.Goods;
import com.rehoshi.model.Manifest;
import com.rehoshi.model.ProductComposition;
import com.rehoshi.service.GoodsService;
import com.rehoshi.util.CollectionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private ProductCompositionMapper productCompositionMapper ;

    @Resource
    private ManifestMapper manifestMapper ;

    @Override
    public RespData<String> save(Goods goods) {
        return null;
    }

    @Override
    public RespData<Boolean> update(Goods goods) {
        return null;
    }

    @Override
    public RespData<Boolean> deleteById(String id) {
        return null;
    }

    /**
     * 查询所有商品
     *
     * @return
     */
    @Override
    public RespData<List<Goods>> getAllGoods() {
        List<Goods> allGoods = goodsMapper.getAllGoods();
        return RespData.success(allGoods).setCode(200).setMsg("成功");
    }

    @Override
    public RespData<List<Goods>> getAllMaterial() {
        return null;
    }

    @Override
    public RespData<List<Goods>> getAllPackageMaterial() {
        return null;
    }

    /**
     * @param search    需要查找的关键字
     * @param pageIndex 页码
     * @param pageSize  每页数据量
     * @return
     */
    @Override
    public PageData<Goods> goodsInPage(GoodPageSearch search, int pageIndex, int pageSize) {
        //查询之前只需传入页码及每页的大小
        PageHelper.startPage(pageIndex, pageSize);
        //startPage后面紧跟的查询就是分页查询
        List<Goods> goods = goodsMapper.queryGoodsBySearch(search);
        if(search.getWithSendAmount()){
            CollectionUtil.foreach(goods, data -> {
                data.setSendAmount(getGoodsSendAmount(data));
            });
        }
        PageInfo<Goods> goodsPageInfo = new PageInfo<>(goods);
        return new PageData<>(goodsPageInfo);
    }


    /**
     * 获取商品发货量
     */
    private Double getGoodsSendAmount(Goods goods){
        //获取已发货的订单
        List<Manifest> sendManifestByGid = manifestMapper.getSendManifestByGid(goods.getId());
        AtomicReference<Double> sendAmount = new AtomicReference<>(0d);
        CollectionUtil.foreach(sendManifestByGid, data -> {
            //获取生产原料
            List<ProductComposition> copsList = productCompositionMapper.getByProductIdAndGid(data.getPid(), data.getGid());
            CollectionUtil.foreach(copsList, cops -> {
                sendAmount.updateAndGet(v -> v + cops.getSpecsValue() * cops.getAmount() * data.getAmount());
            });
        });
        return sendAmount.get() ;
    }

    @Override
    public RespData<Goods> getById(String id) {
        return queryGoodById(id);
    }


    /**
     * 添加商品类型
     *
     * @param good
     * @return
     */
    public RespData addGoodsType(Goods good) {
        //生成主键
        String uuid = BaseModel.generateUUID();
        good.setId(uuid);
        good.judgeSpecsValue();
        int result = goodsMapper.addGoodsType(good);
        if (result == 1) {
            return RespData.success(uuid).setCode(200).setMsg("数据库更新成功");
        } else {
            return RespData.fail(false).setCode(0).setMsg("数据库更新异常");
        }
    }

    /**
     * 删除商品
     *
     * @param id
     * @return
     */
    public RespData<Boolean> delGoodsType(String id) {
        int result = goodsMapper.delGoodsType(id);
        if (result == 1) {
            return RespData.success(true).setCode(200).setMsg("删除成功");
        } else {
            return RespData.fail(false).setCode(0).setMsg("删除失败");
        }
    }

    /**
     * 修改商品
     *
     * @param good
     * @return
     */
    public RespData<Boolean> editGoods(Goods good) {
        good.judgeSpecsValue();
        int result = goodsMapper.editGoods(good);
        if (result == 0) {
            return RespData.fail(false).setCode(0).setMsg("更新异常");
        } else {
            return RespData.success(true).setCode(200).setMsg("成功更新");
        }
    }

    /**
     * 批量删除商品
     *
     * @param goodslist
     * @return
     */
    public RespData<Boolean> delBatchGoodTypes(List<Goods> goodslist) {

        int result = goodsMapper.delBatchGoodTypes(goodslist);
        if (result != 0) {
            return RespData.success(true).setCode(200).setMsg("删除成功");
        } else {
            return RespData.fail(false).setCode(0).setMsg("删除失败");
        }
    }

    /**
     * 根据id查询商品
     *
     * @param id
     * @return
     */
    public RespData<Goods> queryGoodById(String id) {

        Goods goods = goodsMapper.queryGoodSByID(id);
        if (goods != null) {
            return RespData.success(goods).setCode(200).setMsg("查询成功");
        } else {
            return RespData.fail(goods).setCode(0).setMsg("失败");
        }
    }
}
