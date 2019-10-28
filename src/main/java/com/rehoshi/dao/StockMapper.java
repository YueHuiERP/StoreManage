package com.rehoshi.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rehoshi.dto.search.StockPageSearch;
import com.rehoshi.model.Stock;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StockMapper extends BaseMapper<Stock> {

    @Select("SELECT *," +
            "(SELECT SUM(weight) FROM `waste` WHERE sId = s.id) " +
            "FROM `stock` s WHERE id = #{id}")
    @Results({
            @Result(column = "gId", property = "goods", one = @One(
                    select = "com.rehoshi.dao.GoodsMapper.queryGoodSByID"
            )),
            @Result(column = "creatorId", property = "creator", one = @One(
                    select = "com.rehoshi.dao.UserMapper.getById"
            )),
    })
    Stock getById(@Param("id") String id);

    @Select({"<script>",
            "SELECT oriStock.*, ss.stockId, ss.remainAmount",
            "FROM (SELECT s.id stockId, ROUND((IFNULL(amount * IFNULL(specsValue,1.00),0.00)  - IFNULL((SELECT SUM(amount * pAmount * specsValue) FROM productcops WHERE sId = s.id),0.00) - IFNULL((SELECT SUM(weight) FROM waste WHERE sId = s.id),0.00)), 2) remainAmount FROM stock s) ss",
            "LEFT JOIN stock oriStock ON ss.stockId = oriStock.id",
            "WHERE oriStock.name LIKE #{name}",
            "<if test='stockType == 0'>",
            "AND parentId IS NULL",
            "</if>",
            "<if test='stockType == 1'>",
            "AND parentId IS NOT NULL",
            "</if>",
            "<if test=\"minRemain != null\">",
            "AND ss.remainAmount > #{minRemain}",
            "</if>",
            "<if test=\"goodsType != null\">",
            "AND (SELECT type FROM goods g WHERE g.id = oriStock.gId) = #{goodsType}",
            "</if>",
            "AND oriStock.createTime BETWEEN #{startTime} AND #{endTime} ORDER BY oriStock.createTime DESC",
            "</script>"
    })
    @Results({
            @Result(column = "gId", property = "goods", one = @One(
                    select = "com.rehoshi.dao.GoodsMapper.queryGoodSByID"
            )),
            @Result(column = "creatorId", property = "creator", one = @One(
                    select = "com.rehoshi.dao.UserMapper.getById"
            )),
    })
    List<Stock> queryStockBySearch(StockPageSearch search);

    @Update({"UPDATE `stock` SET",
            "name=#{name},gId=#{gId},amount=#{amount},price=#{price},",
            "provider=#{provider},description=#{description},batch=#{batch},",
            "offsetAmount=#{offsetAmount}, specsValue=#{specsValue}, supplierId = #{supplierId}",
            "parentId=#{parentId}",
            "WHERE id=#{id}"})
    int editStock(Stock stock);

    @Select({
            "SELECT COUNT(*) FROM `stock`",
            "WHERE to_days(createTime) = to_days(now())"
    })
    int todayCount();

    @Select({
            "SELECT * FROM `stock`",
            "ORDER BY createTime DESC LIMIT 0, 1"
    })
    Stock getLast();
}
