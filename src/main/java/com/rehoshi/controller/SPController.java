package com.rehoshi.controller;

import com.rehoshi.dto.RespData;
import com.rehoshi.model.HoshiModel;
import com.rehoshi.model.User;
import com.rehoshi.service.IHoshiService;
import com.rehoshi.service.UserService;
import com.rehoshi.util.ContextUtil;
import com.rehoshi.util.HStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public abstract class SPController<T extends HoshiModel, S extends IHoshiService<T>> extends HoshiController {

    @Autowired
    UserService userService ;

    @Autowired
    S coreService ;

    @DeleteMapping("/delete/{id}")
    public RespData<Boolean> remove(@PathVariable String id){
        return $(resp->{
            coreService.removeById(id) ;
            resp.success().setData(true).setMsg("删除成功") ;
        });
    }

    @DeleteMapping("/delete/all")
    public RespData<Boolean> removeAll(@RequestBody List<String> ids){
        return $(resp->{
            coreService.removeByIds(ids) ;
            resp.success().setData(true).setMsg("删除成功") ;
        });
    }

    @PutMapping("/update")
    public RespData<Boolean> update(@RequestBody T entity){
        return $(resp->{
            User curUser = ContextUtil.getCurUser();
            entity.setUpdaterId(curUser.getUpdaterId());
            entity.setUpdateTime(new Date());
            coreService.updateById(entity) ;
            resp.success().setData(true).setMsg("更新成功") ;
        });
    }

    @PostMapping("/add")
    public RespData<Serializable> add(@RequestBody T entity){
        return $(resp->{
            User curUser = ContextUtil.getCurUser();
            entity.setCreatorId(curUser.getId());
            entity.setUpdaterId(curUser.getId());
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());
            coreService.save(entity);
            resp.success().setData(getId(entity)).setMsg("保存成功") ;
        });
    }
    @GetMapping("/list")
    public RespData<List<T>> list(@RequestParam(name = "search", required = false, defaultValue = "")
                                                String search, @RequestParam("page") int pageIndex, @RequestParam("limit") int pageSize) {
        return $(listRespData -> {
            $page().index(pageIndex).size(pageSize);
            List<T> list = coreService.listBySearch(search);
            onSearchFinish(list);
            listRespData.success().setData(list).setMsg("查询完成");
        });
    }

    @GetMapping("/list/{userId}")
    public RespData<List<T>> listFroUser(@RequestParam(name = "search", required = false, defaultValue = "") String num,
                                         @PathVariable String userId,
                                         @RequestParam("page") int pageIndex, @RequestParam("limit") int pageSize) {
        return $(listRespData -> {
            $page().index(pageIndex).size(pageSize);
            List<T> list = coreService.listBySearch(num, userId);
            onSearchFinish(list);
            listRespData.success().setData(list).setMsg("查询完成");
        });
    }

    protected void onSearchFinish(List<T> list){
        HStream.$(list).forEach(t -> {
            t.setUpdater(userService.getById(t.getUpdaterId()).data);
            t.setCreator(userService.getById(t.getCreatorId()).data);
        });
    }

    protected Serializable getId(T entity){
        try {
            Field id = entity.getClass().getDeclaredField("id");
            id.setAccessible(true);
            return (Serializable) id.get(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }
}
