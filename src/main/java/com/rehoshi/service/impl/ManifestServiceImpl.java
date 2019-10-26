package com.rehoshi.service.impl;

import com.github.pagehelper.PageHelper;
import com.rehoshi.dao.GoodsMapper;
import com.rehoshi.dao.ManifestMapper;
import com.rehoshi.dao.ProductCompositionMapper;
import com.rehoshi.dao.ProductMapper;
import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.ManifestPageSearch;
import com.rehoshi.model.*;
import com.rehoshi.service.ManifestService;
import com.rehoshi.util.CollectionUtil;
import com.rehoshi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ManifestServiceImpl implements ManifestService {

    @Resource
    private ManifestMapper manifestMapper;
    @Resource
    private ProductCompositionMapper productCompositionMapper;
    @Resource
    private ProductMapper productMapper;

    @Override
    public RespData<String> save(Manifest manifest) {
        RespData<String> data = RespData.fail("").setMsg("添加订单失败");
        if (manifest == null) {
            data.setMsg("请输入订单信息");
        } else if (manifest.getStatus() == Manifest.Status.WAIT_SEND && StringUtil.isNullOrEmpty(manifest.getGid())) {
            data.setMsg("请选择商品");
        } else if (manifest.getStatus() == Manifest.Status.HAS_SENT && StringUtil.isNullOrEmpty(manifest.getPid())) {
            data.setMsg("请选择成品");
        } else {
            manifest.newId();

            if (manifest.getStatus() == Manifest.Status.HAS_SENT) {
                Manifest parent = manifestMapper.getById(manifest.getParentId());
                //保存原料果的id
                manifest.setGid(parent.getGoods().getId());

                assembleChildren(manifest);
            }

            //没有设置创建日期 默认加上
            if (manifest.getCreateTime() == null) {
                manifest.setCreateTime(new Date());
            }

            saveOrderRelation(manifest);

            int save = manifestMapper.save(manifest);
            if (save > 0) {
                data.success().setData(manifest.getId()).setMsg("添加订单成功");
            }
        }
        return data;
    }

    private void assembleChildren(Manifest manifest) {
        //保存其他包材的id
        List<ProductComposition> byProductId = productCompositionMapper.getByProductId(manifest.getPid());
        CollectionUtil.foreach(byProductId, cops -> {
            if (!cops.getgId().equals(manifest.getGid())) {
                Manifest child = manifest.newChild();
                child.setGid(cops.getgId());
                manifest.getSubManifests().add(child);
            }
        });
    }

    @Override
    public RespData<Boolean> update(Manifest manifest) {
        RespData<Boolean> respData = RespData.fail(false).setMsg("更新失败");
        if (manifest.getStatus() == Manifest.Status.WAIT_SEND && StringUtil.isNullOrEmpty(manifest.getGid())) {
            respData.setMsg("请选择商品");
        } else if (manifest.getStatus() == Manifest.Status.HAS_SENT && StringUtil.isNullOrEmpty(manifest.getPid())) {
            respData.setMsg("请选择成品");
        } else {
            //先删除之前的关系 已发货
            if (manifest.getStatus() == Manifest.Status.HAS_SENT) {
                manifestMapper.deleteByParentId(manifest.getId());
                manifest.setSubManifests(new ArrayList<>());
                assembleChildren(manifest);
            }
            int update = manifestMapper.update(manifest);
            if (update > 0) {

                //重新保存关系
                saveOrderRelation(manifest);

                respData.success().setData(true).setMsg("更新成功");
            }
        }
        return respData;
    }

    private void saveOrderRelation(Manifest manifest) {
        if (manifest.getStatus() == Order.Status.HAS_SENT) {
            manifest.newChildren();
            if (!CollectionUtil.isNullOrEmpty(manifest.getSubManifests())) {
                manifestMapper.saveAll(manifest.getSubManifests());
            }
        }
    }

    @Override
    public RespData<Boolean> deleteById(String id) {
        RespData<Boolean> respData = RespData.fail(false).setMsg("删除订单失败");
        if (manifestMapper.deleteById(id) > 0) {
            manifestMapper.deleteByParentId(id);
            respData.setData(true).success().setMsg("删除订单成功");
        }
        return respData;
    }

    @Override
    public RespData<Boolean> deleteInIds(List<String> ids) {
        RespData<Boolean> respData = RespData.fail(false).setMsg("批量删除订单失败");
        if (!CollectionUtil.isNullOrEmpty(ids)) {
            if (manifestMapper.deleteInIds(ids) > 0) {
                manifestMapper.deleteInParentIds(ids);
                respData.setData(true).success().setMsg("批量删除订单成功");
            }
        }
        return respData;
    }

    @Override
    public PageData<Manifest> listInPage(ManifestPageSearch search) {
        PageHelper.startPage(search.getPageIndex(), search.getPageSize());
        return new PageData<>(assembleManifestList(manifestMapper.listInPage(search)));
    }

    @Override
    public RespData<Manifest> getById(String id) {
        RespData<Manifest> data = new RespData<Manifest>().setData(null).setMsg("查询订单失败");
        Manifest byId = manifestMapper.getById(id);
        if (byId != null) {
            assembleManifest(byId);
            data.success().setData(byId).setMsg("查询订单成功");
        }
        return data;
    }

    /**
     * 生成已发货订单
     *
     * @param gId
     * @return
     */
    @Override
    public RespData<List<Manifest>> batchPrepareListByGid(String gId) {
        RespData<List<Manifest>> data = new RespData<List<Manifest>>().setData(null).setMsg("查询订单失败");
        List<Manifest> byStatusAndGid = manifestMapper.getByStatusAndGid(Manifest.Status.WAIT_SEND, gId);
        if (byStatusAndGid != null) {
            //装配扩展属性
            assembleManifestList(byStatusAndGid);
            List<Manifest> listTpl = CollectionUtil.find(byStatusAndGid, manifest -> {
                Double remainAmount = manifest.getRemainAmount();
                boolean b = remainAmount > 0;
                return manifest.getRemainAmount() > 0;
            });
            //获取默认推荐使用的成品
            Product recommendProduct = productMapper.getRecommendByGid(gId);
            Date date = new Date();

            //直接返回默认创建好的剩余数量大于0的已发货订单
            CollectionUtil.foreach(listTpl, (manifest, index) -> {
                if (recommendProduct != null) {
                    manifest.setPid(recommendProduct.getId());
                    manifest.setProduct(recommendProduct);
                }
                manifest.setParentId(manifest.getId());
                manifest.setStatus(Manifest.Status.HAS_SENT);
                manifest.setCreateTime(date);
                manifest.setId(null);
            });
            data.success().setData(listTpl).setMsg("查询订单成功");
        }
        return data;
    }

    @Override
    public RespData<Boolean> save(List<Manifest> manifestList) {
        RespData<Boolean> respData = RespData.fail(false).setMsg("批量保存失败");
        //重新设置id
        CollectionUtil.foreach(manifestList, BaseModel::newId);


        if (manifestMapper.saveAll(manifestList) > 0) {
            respData.success().setData(true).setMsg("批量保存成功");
        }

        return respData;
    }

    private List<Manifest> assembleManifestList(List<Manifest> manifestList) {
        CollectionUtil.foreach(manifestList, this::assembleManifest);
        return manifestList;
    }

    private void assembleManifest(Manifest manifest) {
        manifest.setSendAmount(manifestMapper.getSendAmountById(manifest.getId()));
        manifest.setSubManifests(manifestMapper.getByParentId(manifest.getId()));
    }
}
