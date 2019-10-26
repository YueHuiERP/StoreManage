package com.rehoshi.dao;

import com.rehoshi.dto.search.ProductPageSearch;
import com.rehoshi.model.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProductMapper {

    /**
     * 根据查询条件查询
     *
     * @param search 查询条件
     * @return
     */
    @Select({"SELECT * FROM `product`",
            "WHERE name LIKE #{name}",
            "AND createTime BETWEEN #{startTime} AND #{endTime}",
            "ORDER BY createTime DESC"
    })
    List<Product> getBySearch(ProductPageSearch search);

    @Select("SELECT * from product WHERE id = #{id}")
    @Results({
            @Result(column = "id",
                    property = "compositions",
                    many = @Many(select = "com.rehoshi.dao.ProductCompositionMapper.getByProductId")),
            @Result(column = "id",
                    property = "remainAmount",
                    many = @Many(select = "com.rehoshi.dao.StatisticsMapper.getProductSendAmount"))
    })
    Product getById(@Param("id") String id) ;


    /**
     * 根据id 获取产品的概括信息 不包含产品成分
     */
    @Select("SELECT * from product WHERE id = #{id}")
    Product getOutlineById(@Param("id") String id) ;

    /**
     * 更新产品
     *
     * @param product 产品信息
     * @return
     */
    @Update({
            "UPDATE product",
            "SET name = #{name},",
            "specs = #{specs},",
            "createTime = #{createTime},",
            "packFee = #{packFee},",
            "amount = #{amount}",
            "WHERE id = #{id}"
    })
    int update(Product product);

    /**
     * 删除产品
     *
     * @param id 需要删除的产品id
     * @return
     */
    @Delete({"DELETE FROM product WHERE id = #{id}"})
    int deleteById(String id);

    /**
     * 批量删除产品
     *
     * @param ids 需要删除的产品 id列表
     * @return
     */
    @Delete({
            "<script>",
            "DELETE FROM product",
            "WHERE id IN",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    int deleteInIds(@Param("ids") List<String> ids);

    /**
     * 保存产品
     * @param product 需要保存的产品信息
     * @return
     */
    @Insert({
            "INSERT",
            "INTO product (",
            "id,","name,","createTime,","specs,","packFee,","amount",
            ") VALUES (",
            "#{id},","#{name},","#{createTime},","#{specs},","#{packFee},","#{amount})"
    })
    int save(Product product);


    /**
     * 查询所有商品
     * @return
     */
    @Select("SELECT * FROM product")
    List<Product> queryAllProduct();


    /**
     * 获取根据商品id 获取最新生产的成品
     * 原料包含对应商品
     * 剩余数量大于0
     * @param gId
     * @return
     */
    @Select({"SELECT * FROM `product` p",
            "WHERE p.id IN ( SELECT pId FROM `productcops` WHERE gId = #{gId} ) AND amount - IFNULL((SELECT SUM(amount)FROM `manifest`WHERE pId = p.id AND visible = 1),0) > 0 ",
            "ORDER BY createTime DESC LIMIT 0, 1"
    })
    Product getRecommendByGid(@Param("gId") String gId);
}
