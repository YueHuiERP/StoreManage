package com.rehoshi.service.impl;

import com.github.pagehelper.PageHelper;
import com.rehoshi.dao.SupplierMapper;
import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.SupplierPageSearch;
import com.rehoshi.model.Supplier;
import com.rehoshi.service.SupplierService;
import com.rehoshi.util.CollectionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Resource
    private SupplierMapper supplierMapper;

    @Override
    public PageData<Supplier> listInPage(SupplierPageSearch search) {
        PageHelper.startPage(search.getPageIndex(), search.getPageSize());
        List<Supplier> bySearch = supplierMapper.getBySearch(search);
        return new PageData<>(bySearch);
    }

    @Override
    public RespData<Supplier> getById(String id) {
        RespData<Supplier> respData = new RespData<Supplier>().fail().setMsg("查询失败");
        Supplier byId = supplierMapper.getById(id);
        if (byId != null) {
            respData.success().setData(byId).setMsg("查询成功");
        }
        return respData;
    }

    @Override
    public RespData<String> save(Supplier supplier) {
        RespData<String> respData = new RespData<String>().fail().setMsg("新增失败");
        supplier.newId();
        supplier.setCreateTime(new Date());
        if (supplierMapper.save(supplier) > 0) {
            respData.success().setData(supplier.getId()).setMsg("新增成功");
        }
        return respData;
    }

    @Override
    public RespData<Boolean> update(Supplier supplier) {
        RespData<Boolean> respData = new RespData<Boolean>().fail().setData(false).setMsg("更新失败");
        if (supplierMapper.update(supplier) > 0) {
            respData.success().setData(true).setMsg("更新成功");
        }
        return respData;
    }

    @Override
    public RespData<Boolean> deleteById(String id) {
        RespData<Boolean> respData = new RespData<Boolean>().fail().setData(false).setMsg("删除失败");
        if (supplierMapper.deleteById(id) > 0) {
            respData.success().setData(true).setMsg("删除成功");
        }
        return respData;
    }

    @Override
    public RespData<Boolean> deleteInIds(List<String> ids) {
        RespData<Boolean> respData = new RespData<Boolean>().fail().setData(false).setMsg("批量删除失败");
        if (CollectionUtil.isNullOrEmpty(ids)) {
            respData.setMsg("请至少选择一条数据") ;
        } else {
            if (supplierMapper.deleteInIds(ids) > 0) {
                respData.success().setData(true).setMsg("批量删除成功");
            }
        }
        return respData;
    }
}
