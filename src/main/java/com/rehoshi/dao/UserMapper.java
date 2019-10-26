package com.rehoshi.dao;

import com.rehoshi.dto.search.UserPageSearch;
import com.rehoshi.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    /**
     * 保存用户数据
     * @return 返回影响行数
     */
    @Insert("INSERT INTO user (id, account, name, password, role, token, createTime) " +
            "VALUES (#{id}, #{account}, #{name}, #{password}, #{role}, #{token}, #{createTime})")
    int save(User user) ;


    /**
     * 根据用户ID 更新用户数据
     * @param user 需要更新的用户数据 需要包含ID
     * @return 返回影响行数
     */
    @Update("UPDATE user SET account = #{account}, name = #{name}, password = #{password}," +
            " role = #{role}, token = #{token}, createTime = #{createTime} WHERE id = #{id}")
    int update(User user) ;

    /**
     * 删除用户
     * @param id 用户id
     * @return 返回影响行数
     */
    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteById(String id) ;

    /**
     * 根据查询条件返回查数据行数
     * @param search
     * @return 返回影响行数
     */
    int countBySearch(String search) ;

    /**
     * 根据条件查找用户 不查询 当前用户 不查询超级管理员
     * @param search 查找内容
     * @return 查询到的用户列表
     */
    @Select("SELECT * FROM user WHERE name like #{name} and id != #{userId} and role != #{role} ORDER BY createTime desc ")
    List<User> getBySearch(UserPageSearch search) ;

    /**
     * 查找用户
     * @param id 需要查找的用户id
     * @return 查找到的用户信息
     */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User getById(String id) ;

    /**
     * 查找用户
     * @param account 需要查找的用户账号
     * @return
     */
    @Select("SELECT * FROM user WHERE account = #{account} LIMIT 0, 1")
    User getByAccount(@Param("account") String account) ;

    @Delete({
            "<script>",
            "DELETE",
            "FROM user",
            "WHERE id IN",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    int deleteInIds(@Param("ids") List<String> ids);
}
