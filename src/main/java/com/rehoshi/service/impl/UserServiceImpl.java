package com.rehoshi.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rehoshi.dao.UserMapper;
import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.UserPageSearch;
import com.rehoshi.model.User;
import com.rehoshi.service.UserService;
import com.rehoshi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public RespData<User> login(User user) {
        RespData<User> data = new RespData<User>().fail().setData(null).setMsg("账号或者密码错误") ;
        if(user != null){
            User byAccount = userMapper.getByAccount(user.getAccount());
            if(byAccount != null){
                String password = byAccount.getPassword();
                if(password.equals(user.getPassword())){
                    data.success().setData(byAccount).setMsg("登录成功") ;
                }
            }
        }
        return data;
    }

    @Override
    public PageData<User> usersInPage(UserPageSearch search, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<User> bySearch = userMapper.getBySearch(search);
        PageInfo<User> userPageInfo = new PageInfo<>(bySearch);
        return new PageData<>(userPageInfo);
    }

    @Override
    public RespData<User> getById(String id) {
        return null;
    }

    @Override
    public RespData<String> save(User user) {
        RespData<String> data = RespData.fail(null);
        if (user != null) {
            //先根据账号查找用户
            User byAccount = userMapper.getByAccount(user.getAccount());
            if (byAccount == null) {
                user.newId();
                user.setCreateTime(new Date());
                int save = userMapper.save(user);
                if (save > 0) {
                    data.success().setMsg("添加用户成功");
                } else {
                    data.fail().setMsg("添加用户失败");
                }
            } else {
                data.fail().setMsg("账号已存在");
            }
        }
        return data;
    }

    @Override
    public RespData<Boolean> update(User user) {
        RespData<Boolean> data = RespData.fail(false);
        if (user != null) {
            User byAccount = userMapper.getByAccount(user.getAccount());
            if (byAccount == null || byAccount.getId().equalsIgnoreCase(user.getId())) {
                int update = userMapper.update(user);
                if (update > 0) {
                    data.success().setData(true).setMsg("更新成功");
                } else {
                    data.setMsg("更新失败");
                }
            } else {
                data.setData(false).setMsg("账号已存在") ;
            }
        }
        return data;
    }

    @Override
    public RespData<Boolean> deleteById(String id) {
        RespData<Boolean> data = RespData.fail(false) ;
        int count = userMapper.deleteById(id);
        if(count > 0){
            data.success().setData(true).setMsg("删除成功") ;
        }else {
            data.setMsg("删除失败") ;
        }
        return data;
    }

    @Override
    public User getByAccount(String account) {
        return userMapper.getByAccount(account);
    }

    @Override
    public RespData<Boolean> deleteInIds(List<String> ids) {
        RespData<Boolean> data = RespData.fail(false) ;
        int index = userMapper.deleteInIds(ids);
        if(index > 0){
            data.success().setData(true).setMsg("删除成功") ;
        }else {
            data.setMsg("删除失败") ;
        }
        return data;
    }

    @Override
    public RespData<Boolean> changePassword(User user) {
        RespData<Boolean> data = RespData.fail(false) ;
        if(StringUtil.isNullOrEmpty(user.getOldPassword())){
            data.setMsg("请输入旧密码") ;
        }else if(StringUtil.isNullOrEmpty(user.getPassword())){
            data.setMsg("请输入新密码") ;
        }else if(!user.getPassword().equals(user.getRePassword())){
            data.setMsg("两次密码不一致") ;
        }else if(user.getOldPassword().equals(user.getPassword())){
            data.setMsg("新旧密码不能相同") ;
        }else{
            User byId = userMapper.getById(user.getId());
            if(user.getOldPassword().equals(byId.getPassword())){
                byId.setPassword(user.getPassword());
                int update = userMapper.update(byId);
                if(update > 0){
                    data.success().setData(true).setMsg("修改成功") ;
                }else {
                    data.setMsg("修改失败") ;
                }
            }else {
                data.setMsg("旧密码验证失败") ;
            }
        }

        return data;
    }
}
