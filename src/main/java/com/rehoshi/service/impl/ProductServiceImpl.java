package com.rehoshi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.rehoshi.dao.ProductCompositionMapper;
import com.rehoshi.dao.ProductMapper;
import com.rehoshi.dao.StatisticsMapper;
import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.ProductPageSearch;
import com.rehoshi.model.Product;
import com.rehoshi.model.ProductComposition;
import com.rehoshi.service.ProductService;
import com.rehoshi.util.CollectionUtil;
import com.rehoshi.util.DateUtil;
import com.rehoshi.util.MapUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private ProductCompositionMapper productCompositionMapper;

    @Resource
    private StatisticsMapper statisticsMapper ;

    @Override
    public RespData<String> packing(Product product) {
        RespData<String> data = RespData.fail("").setMsg("成品添加失败");
        if (product != null) {
            if (CollectionUtil.isNullOrEmpty(product.getCompositions())) {
                data.setMsg("请选择成品原料");
            } else {
                product.newId();
                product.setCreateTime(new Date());
                //保存产品
                int save = productMapper.save(product);
                if (save > 0) {
                    //保存产品原料
                    List<ProductComposition> compositions = product.getCompositions();
                    newCompositionsId(product);
                    int copsCount = productCompositionMapper.save(compositions);
                    if (copsCount == compositions.size()) {
                        data.success().setData(product.getId()).setMsg("成品添加成功");
                    } else {
                        data.setMsg("成品原料保存失败");
                    }

                }
            }
        }
        return data;
    }

    @Override
    public PageData<Product> productInPage(ProductPageSearch search, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Product> bySearch = productMapper.getBySearch(search);
        CollectionUtil.foreach(bySearch,data -> {
            data.setSendAmount(statisticsMapper.getProductSendAmount(data.getId()));
        });
        return new PageData<>(bySearch);
    }

    @Override
    public RespData<Product> getById(String id) {
        RespData<Product> data = new RespData<Product>().setData(null).setMsg("查询失败");
        Product byId = productMapper.getById(id);
        if (byId != null) {
            byId.setId(id);
            data.success().setData(byId).setMsg("查询成功");
        }
        return data;
    }

    @Override
    public RespData<Boolean> update(Product product) {
        RespData<Boolean> data = RespData.fail(false).setMsg("成品修改失败");
        if (product != null) {
            if (DateUtil.isAfterNow(product.getCreateTime())) {
                data.setMsg("创建时间不能超过当前时间");
            } else {
                if (CollectionUtil.isNullOrEmpty(product.getCompositions())) {
                    data.setMsg("请添加成品原料");
                } else {
                    //重新设置id 和 关系
                    newCompositionsId(product);
                    //先删除关系
                    int copsCount;
                    productCompositionMapper.deleteByProductId(product.getId());
                    int update = productMapper.update(product);
                    if (update > 0) {
                        copsCount = productCompositionMapper.save(product.getCompositions());
                        if (copsCount > 0) {
                            data.success().setData(true).setMsg("成品修改成功");
                        } else {
                            data.setMsg("成品原料添加失败");
                        }
                    }
                }
            }
        }
        return data;
    }

    @Override
    public RespData<Boolean> deleteById(String id) {
        RespData<Boolean> data = RespData.fail(false).setMsg("成品删除失败");
        int i = productMapper.deleteById(id);
        if (i > 0) {
            productCompositionMapper.deleteByProductId(id);
            data.success().setData(true).setMsg("成品删除成功");
        }
        return data;
    }

    @Override
    public RespData<Boolean> deleteInIds(List<String> ids) {
        RespData<Boolean> data = RespData.fail(false).setMsg("成品批量删除失败");
        int i = productMapper.deleteInIds(ids);
        if (i > 0) {
            productCompositionMapper.deleteByProductIdInIds(ids);
            data.success().setData(true).setMsg("成品批量删除成功");
        }
        return data;
    }

    @Override
    public RespData<List<Product>> list(ProductPageSearch search) {
        List<Product> bySearch = productMapper.getBySearch(search);
        CollectionUtil.foreach(bySearch, data -> {
            data.setCompositions(productCompositionMapper.getByProductId(data.getId()));
        });
        CollectionUtil.foreach(bySearch,data -> {
            data.setSendAmount(statisticsMapper.getProductSendAmount(data.getId()));
        });
        return RespData.success(bySearch);
    }

    @Override
    public RespData<List<Product>> getByStockId(String stockId) {
        //查找到所有的原料
        List<ProductComposition> cops = productCompositionMapper.selectByMap(MapUtil.byPair("sId", stockId));
        //查找到所有产品
        List<Product> products = baseMapper.selectBatchIds(CollectionUtil.map(cops, ProductComposition::getpId));
        CollectionUtil.foreach(products, data -> {
            data.setCompositions(productCompositionMapper.getByProductId(data.getId()));
        });
        return RespData.success(products).setMsg("查询成功");
    }

    public static void newCompositionsId(Product product) {
        CollectionUtil.foreach(product.getCompositions(), data -> {
            data.setpId(product.getId());
            data.newId();
            data.setpAmount(product.getAmount());
            data.judgeSpecsValue();
        });
    }
}
