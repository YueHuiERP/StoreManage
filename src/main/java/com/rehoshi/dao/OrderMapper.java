package com.rehoshi.dao;

import com.rehoshi.dto.search.OrderPageSearch;
import com.rehoshi.model.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrderMapper {


    /**
     * 根据条件查找订单
     *
     * @param search 查询条件
     * @return
     */
    @Select({"<script>",
            "SELECT * FROM `order`",
            "WHERE name LIKE #{name}",
            "AND createTime BETWEEN #{startTime} AND #{endTime}",
            "<when test = 'status != null'>",
            "AND status = #{status}",
            "</when>",
            "</script>"})
    @Results({
            @Result(column = "pId", property = "product", one = @One(select = "com.rehoshi.dao.ProductMapper.getById")),
    })
    List<Order> getBySearch(OrderPageSearch search);

    /**
     * 根据ID删除订单
     *
     * @param id
     */
    @Delete("DELETE FROM `order` WHERE id = #{id}")
    int deleteById(@Param("id") String id);

    @Delete("DELETE FROM `order` WHERE parentId = #{id}")
    int deleteByParentId(@Param("id") String id) ;

    /**
     * 根据id更新订单
     *
     * @param order 需要更新的订单信息
     * @return
     */
    @Update("UPDATE `order` SET name = #{name}, serial = #{serial}, " +
            "pId = #{pId}, amount = #{amount}, " +
            "createTime = #{createTime}, status = #{status}, parentId = #{parentId} WHERE id = #{id}")
    int update(Order order);

    /**
     * 添加订单
     *
     * @param order 需要添加的订单信息
     * @return
     */
    @Insert("INSERT INTO `order` (id, name, serial, pId, amount, createTime, status, parentId) " +
            "VALUES (#{id}, #{name}, #{serial}, #{pId}, #{amount}, #{createTime}, #{status}, #{parentId})")
    int save(Order order);

    /**
     * 批量删除订单
     *
     * @param ids
     * @return
     */
    @Delete({"<script>",
            "DELETE",
            "FROM `order`",
            "WHERE id IN ",
            "<foreach collection = 'ids' item = 'id' open='(' separator = ',' close = ')' >",
            "#{id}",
            "</foreach>",
            "</script>"})
    int deleteInIds(@Param("ids") List<String> ids);

    /**
     * 批量删除订单
     *
     * @param ids
     * @return
     */
    @Delete({"<script>",
            "DELETE",
            "FROM `order`",
            "WHERE parentId IN ",
            "<foreach collection = 'ids' item = 'id' open='(' separator = ',' close = ')' >",
            "#{id}",
            "</foreach>",
            "</script>"})
    int deleteInParentIds(@Param("ids") List<String> ids);


    /**
     * 根据ID 查找订单
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM `order` WHERE id = #{id}")
    @Results({
            @Result(column = "pId", property = "product", one = @One(
                    select = "com.rehoshi.dao.ProductMapper.getOutlineById"
            )),
    })
    Order getById(String id);


    /**
     * 根据父id查找子订单
     *
     * @param id 需要查找的父id
     * @return
     */
    @Select({
            "SELECT *",
            "FROM `order`",
            "WHERE parentId = #{id}"
    })
    @Results({
            @Result(column = "pId", property = "product", one = @One(
                    select = "com.rehoshi.dao.ProductMapper.getOutlineById"
            ))
    })
    List<Order> getByParentId(@Param("id") String id);


    @Insert({"<script>",
            "INSERT INTO `order`(id, name, serial, pId, amount, createTime, status, parentId) VALUES ",
            "<foreach collection='orderList' item='item' index='index' separator=','>",
            "(#{item.id}, #{item.name}, #{item.serial}, #{item.pId}, #{item.amount}, #{item.createTime}, #{item.status}, #{item.parentId})",
            "</foreach>",
            "</script>"})
    int saveAll(@Param("orderList") List<Order> orderList);
}
