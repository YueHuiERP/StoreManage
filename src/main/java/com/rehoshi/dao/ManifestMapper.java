package com.rehoshi.dao;

import com.rehoshi.dto.search.ManifestPageSearch;
import com.rehoshi.model.Manifest;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ManifestMapper {

    /**
     * 保存订单
     */
    @Insert({"INSERT INTO `manifest`",
            "(id, name, pid, amount, status, parentId, gid, createTime, visible)",
            "VALUES",
            "(#{id}, #{name}, #{pid}, #{amount}, #{status}, #{parentId}, #{gid}, #{createTime}, #{visible})"})
    int save(Manifest manifest);

    /**
     * 更新订单
     */
    @Update({"UPDATE `manifest` SET",
            "name = #{name}, pid = #{pid}, amount = #{amount}, status = #{status}, ",
            "parentId = #{parentId}, gid = #{gid}, createTime = #{createTime}, visible= #{visible} WHERE id = #{id}"})
    int update(Manifest manifest);

    /**
     * 根据id删除
     */
    @Delete("DELETE FROM `manifest` WHERE id = #{id}")
    int deleteById(@Param("id") String id);

    /**
     * 批量删除订单
     *
     * @param ids
     * @return
     */
    @Delete({"<script>",
            "DELETE",
            "FROM `manifest`",
            "WHERE id IN ",
            "<foreach collection = 'ids' item = 'id' open='(' separator = ',' close = ')' >",
            "#{id}",
            "</foreach>",
            "</script>"})
    int deleteInIds(@Param("ids") List<String> ids);

    /**
     * 根据ID 查找订单
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM `manifest` WHERE id = #{id}")
    @Results({
            @Result(column = "pid", property = "product", one = @One(
                    select = "com.rehoshi.dao.ProductMapper.getOutlineById"
            )),
            @Result(column = "gid", property = "goods", one = @One(
                    select = "com.rehoshi.dao.GoodsMapper.queryGoodSByID"
            )),
    })
    Manifest getById(@Param("id") String id);

    /**
     * 分页查询订单
     */
    @Select({"<script>",
            "SELECT * FROM `manifest`",
            "WHERE name LIKE #{name}",
            "AND createTime BETWEEN #{startTime} AND #{endTime}",
            "<when test = 'status != null'>",
            "AND status = #{status} AND visible = 1",
            "</when>",
            "ORDER BY createTime DESC",
            "</script>"})
    @Results({
            @Result(column = "pid", property = "product", one = @One(
                    select = "com.rehoshi.dao.ProductMapper.getOutlineById"
            )),
    })
    List<Manifest> listInPage(ManifestPageSearch search);

    /**
     * 根据父id查找子订单
     *
     * @param id 需要查找的父id
     * @return
     */
    @Select({
            "SELECT *",
            "FROM `manifest`",
            "WHERE parentId = #{id}"
    })
    @Results({
            @Result(column = "pid", property = "product", one = @One(
                    select = "com.rehoshi.dao.ProductMapper.getOutlineById"
            ))
    })
    List<Manifest> getByParentId(String id);

    /**
     * 根据父id
     * 批量删除订单
     *
     * @param ids
     * @return
     */
    @Delete({"<script>",
            "DELETE",
            "FROM `manifest`",
            "WHERE parentId IN ",
            "<foreach collection = 'ids' item = 'id' open='(' separator = ',' close = ')' >",
            "#{id}",
            "</foreach>",
            "</script>"})
    int deleteInParentIds(@Param("ids") List<String> ids);

    @Delete("DELETE FROM `manifest` WHERE parentId = #{id}")
    int deleteByParentId(String id);

    @Insert({"<script>",
            "INSERT INTO `manifest`(id, name, pid, gid, amount, createTime, status, parentId, visible) VALUES ",
            "<foreach collection='manifestList' item='item' index='index' separator=','>",
            "(#{item.id}, #{item.name}, #{item.pid},#{item.gid}, #{item.amount}, #{item.createTime}, #{item.status}, #{item.parentId}, #{item.visible})",
            "</foreach>",
            "</script>"})
    int saveAll(@Param("manifestList") List<Manifest> manifestList);

    @Select({
            "SELECT SUM( amount )",
            "FROM `manifest`",
            "WHERE parentId = #{id}",
    })
    Double getSendAmountById(@Param("id") String id);

    @Select({
            "SELECT * FROM `manifest`",
            "WHERE gid = #{gid}",
            "AND parentId IS NOT NULL"
    })
    List<Manifest> getSendManifestByGid(String gid);

    @Select({
            "SELECT * FROM `manifest`",
            "WHERE gid = #{gid}",
            "AND status = #{status}"
    })
    List<Manifest> getByStatusAndGid(@Param("status") int status, @Param("gid") String gid);
}
