package com.rehoshi.dao;

import com.rehoshi.dto.search.SupplierPageSearch;
import com.rehoshi.model.Supplier;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SupplierMapper {

    @Select({"SELECT *",
            "FROM `supplier`",
            "WHERE name LIKE #{name}",
            "AND createTime BETWEEN #{startTime} AND #{endTime}",
            "ORDER BY createTime DESC"
    })
    List<Supplier> getBySearch(SupplierPageSearch search) ;

    @Select({
            "SELECT * ",
            "FROM `supplier`",
            "WHERE id = #{id}"
    })
    Supplier getById(@Param("id") String id) ;

    @Update({
            "UPDATE `supplier` SET",
            "name = #{name}, description = #{description}, createTime = #{createTime}",
            "WHERE id = #{id}"
    })
    int update(Supplier supplier) ;

    @Insert({
            "INSERT INTO `supplier`",
            "(id, name, description, createTime)",
            "VALUES",
            "(#{id}, #{name}, #{description}, #{createTime})"
    })
    int save(Supplier supplier) ;

    @Delete({
            "DELETE FROM `supplier`",
            "WHERE id = #{id}"
    })
    int deleteById(@Param("id") String id) ;

    @Delete({
            "<script>",
            "DELETE FROM `supplier`",
            "WHERE id IN",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    int deleteInIds(@Param("ids") List<String> ids) ;
}
