package com.rehoshi.dao;

import com.rehoshi.dto.echart.ChartData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface StatisticsMapper {

    /**
     * 获取剩余库存数量
     *
     * @param id 库存id
     * @return
     */
    @Select({"SELECT (\n" +
            "\tSELECT amount\n" +
            "\tFROM stock WHERE id = '8b6fa0a482eb44ff8cd209da10ed2cde'\n" +
            ") - (\n" +
            "\tSELECT COUNT(amount)\n" +
            "\tFROM productcops WHERE sId = ''\n" +
            ") remainCount"})
    double getRemainStockAmoun(String id);


    /**
     * 获取库存发货量
     *
     * @param id 库存id
     * @return
     */
    @Select({
            "SELECT SUM(pc.amount * od.amount)",
            "FROM(SELECT pId,amount",
            "FROM productcops ",
            "WHERE sId = #{id}) pc ",
            "LEFT JOIN `order` od ON pc.pId = od.pId"
    })
    Double getStockSendAmount(@Param("id") String id);

    /**
     * 获取库存生产量
     *
     * @param id 库存id
     * @return
     */
    @Select({
            "SELECT SUM(amount * pAmount * specsValue) ",
            "FROM productcops ",
            "WHERE sId = #{id}"
    })
    Double getStockProductAmount(@Param("id") String id);

    /**
     * 获取库存损耗的量
     *
     * @param id 库存id
     * @return
     */
    @Select({
            "SELECT SUM(weight)",
            "FROM waste",
            "WHERE sId = #{id}"
    })
    Double getStockWasteAmount(@Param("id") String id);

    /**
     * 获取成品的发货量
     *
     * @param id 成品id
     * @return
     */
    @Select({
            "SELECT SUM(amount) ",
            "FROM `manifest` ",
            "WHERE pId = #{id} AND visible = 1"
    })
    Integer getProductSendAmount(@Param("id") String id);

    @Select({
            "SELECT *",
            "FROM `order`",
            "WHERE "
    })
    List<ChartData> getOutBoundBetween(Date startDate, Date endDate);


    @Select({
            "SELECT SUM(o1.amount * pSpecsValue) ",
            "FROM ((SELECT oo.*,ox.gId,ox.specsValue pSpecsValue FROM (SELECT pc.*, o.id orderId FROM productcops pc LEFT JOIN `order` o ON pc.oId = o.parentId WHERE pc.id = #{id}) ox LEFT JOIN `order` oo ON ox.orderId = oo.parentId) o1 LEFT JOIN productcops p1 ON",
            " o1.pId = p1.pId AND p1.gId = o1.gId)"
    })
    Double getOrderItemSendAmount(@Param("id") String id);
}
