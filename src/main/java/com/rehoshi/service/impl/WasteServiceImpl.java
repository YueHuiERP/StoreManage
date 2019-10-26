package com.rehoshi.service.impl;

import com.github.pagehelper.PageHelper;
import com.rehoshi.dao.WasteMapper;
import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.WastePageSearch;
import com.rehoshi.model.Waste;
import com.rehoshi.service.WasteService;
import com.rehoshi.util.ContextUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class WasteServiceImpl implements WasteService {

    @Resource
    private WasteMapper wasteMapper ;

    @Override
    public RespData<String> save(Waste waste) {
        RespData<String> data = new RespData<String>().fail().setMsg("损耗添加失败") ;
        waste.newId();
        waste.setCreatorId(ContextUtil.getCurUser().getId());
        waste.setCreateTime(new Date());
        int save = wasteMapper.save(waste);
        if(save > 0){
            data.success().setData(waste.getId()).setMsg("损耗添加成功") ;
        }
        return data;
    }

    @Override
    public RespData<Boolean> update(Waste waste) {
        RespData<Boolean> data = RespData.fail(false).setMsg("损耗更新失败") ;
        int update = wasteMapper.update(waste);
        if(update > 0){
            data.success().setData(true).setMsg("损耗更新成功") ;
        }
        return data;
    }

    @Override
    public RespData<Waste> getById(String id) {
        RespData<Waste> data = new RespData<Waste>().fail().setMsg("损耗查询失败") ;
        Waste byId = wasteMapper.getById(id);
        if(byId != null){
            data.success().setData(byId).setMsg("损耗查询成功") ;
        }
        return data;
    }

    @Override
    public RespData<Boolean> deleteById(String id) {
        RespData<Boolean> data = RespData.fail(false).setMsg("损耗删除失败") ;
        int count = wasteMapper.deleteById(id);
        if(count > 0){
            data.success().setData(true).setMsg("损耗删除成功") ;
        }
        return data;
    }

    @Override
    public PageData<Waste> wasteInPage(WastePageSearch search, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize) ;
        List<Waste> bySearch = wasteMapper.getBySearch(search);
        return new PageData<>(bySearch);
    }

    @Override
    public RespData<Boolean> deleteInIds(List<String> ids) {
        RespData<Boolean> data = RespData.fail(false).setMsg("损耗批量删除失败") ;
        int count = wasteMapper.deleteInIds(ids);
        if(count > 0){
            data.success().setData(true).setMsg("损耗删除成功") ;
        }
        return data;
    }

    @Override
    public RespData<List<Waste>> list(WastePageSearch search) {
        return RespData.success(wasteMapper.getBySearch(search));
    }
}
