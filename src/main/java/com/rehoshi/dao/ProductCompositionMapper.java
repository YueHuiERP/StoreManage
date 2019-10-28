package com.rehoshi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rehoshi.model.ProductComposition;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProductCompositionMapper extends BaseMapper<ProductComposition> {


    @Select("SELECT * FROM productcops WHERE pId = #{id}")
    @Results({
            @Result(column = "sId", property = "stock", one = @One(
                    select = "com.rehoshi.dao.StockMapper.getById"
            ))
    })
    List<ProductComposition> getByProductId(@Param("id") String id);

    @Select("SELECT * FROM productcops WHERE pId = #{id} and gId = #{gid}")
    List<ProductComposition> getByProductIdAndGid(@Param("id") String id, @Param("gid") String gid);

    @Select("SELECT * FROM productcops WHERE oId = #{id}")
    @Results({
            @Result(column = "gId", property = "goods", one = @One(
                    select = "com.rehoshi.dao.GoodsMapper.queryGoodSByID"
            ))
    })
    List<ProductComposition> getByOrderId(@Param("id") String id);


    @Delete("DELETE FROM productcops WHERE pId = #{id} ")
    int deleteByProductId(@Param("id") String id);

    @Delete("DELETE FROM productcops WHERE oId = #{id} ")
    int deleteByOrderId(@Param("id") String id);

    @Delete({"<script>",
            "DELETE",
            "FROM `productcops`",
            "WHERE pId IN ",
            "<foreach collection = 'ids' item = 'id' open='(' separator = ',' close = ')' >",
            "#{id}",
            "</foreach>",
            "</script>"})
    int deleteByProductIdInIds(@Param("ids") List<String> ids);


    @Insert({"<script>",
            "INSERT INTO productcops(id, sId, amount, pId, pAmount, batch, specs, specsUnit, specsValue, gId, oId) VALUES ",
            "<foreach collection='copsList' item='item' index='index' separator=','>",
            "(#{item.id}, #{item.sId}, #{item.amount}, #{item.pId}, ",
            "#{item.pAmount}, #{item.batch}, #{item.specs},",
            "#{item.specsUnit}, #{item.specsValue},",
            "#{item.gId}, #{item.oId}",
            ")",
            "</foreach>",
            "</script>"})
    int save(@Param("copsList") List<ProductComposition> productComposition);

    @Delete({"<script>",
            "DELETE",
            "FROM `productcops`",
            "WHERE oId IN ",
            "<foreach collection = 'ids' item = 'id' open='(' separator = ',' close = ')' >",
            "#{id}",
            "</foreach>",
            "</script>"})
    int deleteByOrderIdInIds(@Param("ids") List<String> ids);
}
