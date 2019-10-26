package com.rehoshi.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.rehoshi.model.Order;
import com.rehoshi.model.ProductComposition;
import com.rehoshi.util.DateUtil;

/**
 * 已发货
 */
public class SendOrderRow extends ExcelRow<Order>{
    @ExcelProperty(value = {"订单名称"},index = 1)
    private String name ;
    @ExcelProperty(value = {"订单状态"},index = 2)
    private String status ;
    @ExcelProperty(value = {"发货时间"},index = 3)
    private String createTime ;
    @ExcelProperty(value = {"成品名称"}, index = 4)
    private String productName;
    @ExcelProperty(value = {"成品数量"}, index = 5)
    private Integer productAmount;

    public SendOrderRow(Order model, Order order) {
        super(model);
        this.name = model.getName() ;
        this.status = model.getStatusStr() ;
        this.createTime = DateUtil.formatDateTime(model.getCreateTime()) ;
        this.productName = order.getProduct().getName() ;
        this.productAmount = order.getAmount() ;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }
}
