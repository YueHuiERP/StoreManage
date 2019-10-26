package com.rehoshi.dao;


import com.rehoshi.dto.search.GoodPageSearch;
import com.rehoshi.model.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GoodsMapper {

    @Select("SELECT * FROM goods")
    List<Goods> getAllGoods();

    @Select("SELECT * FROM goods WHERE id=#{id}")
    Goods queryGoodSByID(String id);

    @Select("SELECT * FROM goods WHERE name=#{name}")
    List<Goods> queryByName(@Param("name") String name);


    @Insert("INSERT INTO goods VALUES (#{id},#{img},#{specs},#{name},#{type},#{specsUnit},#{specsValue})")
    int addGoodsType(Goods good);


    @Select("SELECT * FROM goods WHERE name=#{name} AND type=#{type}")
    List<Goods> queryByNameAndType(@Param("name") String name, @Param("type") Integer type);

    @Delete("DELETE FROM goods WHERE id=#{id}")
    int delGoodsType(@Param("id") String id);


    @Update("UPDATE goods SET img=#{img}, specs=#{specs}, name = #{name},type = #{type}, specsUnit = #{specsUnit}, specsValue = #{specsValue} WHERE id = #{id}")
    int editGoods(Goods good);

    /*
        DELETE FROM goods WHERE id in ('','')
     */
    @Delete({
            "<script>"
                    + "DELETE FROM goods  WHERE id in "
                    + "<foreach item='item' index='index' collection='goodslist' open='(' separator=',' close=')'>"
                    + "#{item.id}"
                    + "</foreach>"
                    + "</script>"
    })
    int delBatchGoodTypes(@Param("goodslist") List<Goods> goodslist);


    @Select({"<script>",
            "SELECT * FROM `goods`",
            "WHERE name LIKE #{name}",
            "AND specsValue > #{minSpecs}",
            "<when test = 'type != null'>",
            "AND type = #{type}",
            "</when>",
            "</script>"})
    List<Goods> queryGoodsBySearch(GoodPageSearch search);
}
