package com.rehoshi.service;

import com.rehoshi.dto.PageData;
import com.rehoshi.dto.RespData;
import com.rehoshi.dto.search.UserPageSearch;
import com.rehoshi.model.User;

import java.util.List;

public interface UserService {
    /**
     * 登录业务
     * 先将密码进行md5加密再查询数据库
     * @param user
     * @return 成功登录返回用户信息 失败返回null
     */
    RespData<User> login(User user) ;

    /**
     * 用户分页数据
     * @param search 查询的输入框的内容
     * @param pageIndex 页码
     * @param pageSize 每页数据量
     * @return
     */
    PageData<User> usersInPage(UserPageSearch search, int pageIndex, int pageSize) ;

    /**
     * 根据id 查找用户
     * @param id 需要查找的用户id
     * @return
     */
    RespData<User> getById(String id) ;

    /**
     * 保存检查账号唯一性
     * 检查完账号之后 重新设置用户UUID
     * @param user 需要保存的用户信息
     * @return 保存成功返回用户id 失败返回null
     */
    RespData<String> save(User user) ;

    /**
     * 根据用户id 修改用户数据
     * @param user
     * @return 成功返回true 失败返回false
     */
    RespData<Boolean> update(User user) ;

    /**
     * 删除用户数据
     * @param id 用户id
     * @return 成功返回true  失败返回 false
     */
    RespData<Boolean> deleteById(String id) ;


    /**
     * 根据账号查询用户
     * @param account 需要查询的用户账号
     * @return
     */
    User getByAccount(String account);

    /**
     * 批量删除用户
     * @param ids 删除所有id在里面的用户
     * @return 成功返回true 失败返回false
     */
    RespData<Boolean> deleteInIds(List<String> ids) ;

    /**
     * 修改用户密码
     * @param user 包含了密码信息的用户数据
     * @return 成功返回true 失败返回false
     */
    RespData<Boolean> changePassword(User user);
}
