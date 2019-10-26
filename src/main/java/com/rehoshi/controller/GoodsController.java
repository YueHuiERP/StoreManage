package com.rehoshi.controller;

import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.GoodPageSearch;
import com.rehoshi.model.Goods;
import com.rehoshi.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 返回所有商品
     *
     * @return
     */
    @RequestMapping(value = "/allgoods", method = RequestMethod.GET)
    public RespData<List<Goods>> getAllGoods() {
        return goodsService.getAllGoods();
    }


    /**
     * 根据id查询商品
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryGoodById/{id}", method = RequestMethod.GET)
    public RespData<Goods> queryGoodById(@PathVariable String id) {
        return goodsService.queryGoodById(id);
    }


    /**
     * 返回商品分页数据
     *
     * @param page  页号
     * @param limit 每页显示多少条数
     * @return
     */
    @RequestMapping(value = "/goodInPage", method = RequestMethod.GET)
    public PageData<Goods> goodInPage(
            @RequestParam(value = "name", required = false) String name
            , @RequestParam(value = "type", required = false) Integer type
            , @RequestParam(value = "page") int page
            , @RequestParam(value = "limit") int limit
            ,@RequestParam(value = "minSpecs", required = false) Integer minSpecs
            ,@RequestParam(value = "goodsType", required = false) Integer goodsType) {


        GoodPageSearch goodPageSearch = new GoodPageSearch();
        goodPageSearch.setName(name);
        goodPageSearch.setType(type);
        goodPageSearch.setMinSpecs(minSpecs);
        return goodsService.goodsInPage(goodPageSearch, page, limit);
    }


    /**
     * 添加商品
     *
     * @param name
     * @param type
     * @param img
     * @param specs
     * @return
     */
    @RequestMapping(value = "/goodsTypeAdd", method = RequestMethod.POST)
    public RespData addGoosType(@RequestParam("name") String name
            , @RequestParam("type") Integer type
            , @RequestParam("img") String img
            , @RequestParam("specs") String specs
            , @RequestParam("specsUnit") String specsUnit) {

        Goods goods = new Goods();
        goods.setName(name);
        goods.setType(type);
        goods.setImg(img);
        goods.setSpecs(specs);
        goods.setSpecsUnit(specsUnit);

        return goodsService.addGoodsType(goods);
    }

    /**
     * 删除单个商品
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delGoodsType/{id}", method = RequestMethod.DELETE)
    public RespData delGoodsType(@PathVariable String id) {
        return goodsService.delGoodsType(id);
    }

    /**
     * 修改单个商品
     *
     * @param good
     * @return
     */
    @RequestMapping(value = "/editGoods", method = RequestMethod.PUT)
    public RespData<Boolean> editGoods(@RequestBody Goods good) {
        return goodsService.editGoods(good);
    }


    /**
     * 批量删除商品
     * RequestBody  指定参数json格式
     *
     * @param goodslist
     * @return
     */
    @RequestMapping(value = "/delBatchGoodsTypes", method = RequestMethod.DELETE)
    public RespData<Boolean> delBatchGoodsTypes(@RequestBody List<Goods> goodslist) {
        return goodsService.delBatchGoodTypes(goodslist);
    }


}
