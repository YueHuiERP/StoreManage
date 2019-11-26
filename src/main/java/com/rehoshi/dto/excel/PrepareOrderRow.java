package com.rehoshi.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.rehoshi.model.Order;
import com.rehoshi.model.ProductComposition;
import com.rehoshi.util.DateUtil;

/**
 * 待发货
 */
public class PrepareOrderRow extends ExcelRow<Order> {

    @ExcelProperty(value = {"订单名称"}, index = 1)
    private String name;

    @ExcelProperty(value = {"订单状态"}, index = 2)
    private String status;

    @ExcelProperty(value = {"发货时间"}, index = 3)
    private String createTime;

    @ExcelProperty(value = {"商品名称"}, index = 4)
    private String goodsName;

    @ExcelProperty(value = {"商品数量"}, index = 5)
    private Double goodsAmount;

    @ExcelProperty(value = {"发货数量"}, index = 6)
    private Double sendAmount;

    public PrepareOrderRow(Order model, ProductComposition cops) {
        super(model);
        this.name = model.getName();
        this.status = model.getStatusStr();
        this.createTime = DateUtil.formatDateTime(model.getCreateTime());
        this.goodsName = cops.getGoods().getName() ;
        this.goodsAmount = cops.getAmount() ;
        this.sendAmount = cops.getSendAmount() / cops.getSpecsValue() ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Double getSendAmount() {
        return sendAmount;
    }

    public void setSendAmount(Double sendAmount) {
        this.sendAmount = sendAmount;
    }
}
