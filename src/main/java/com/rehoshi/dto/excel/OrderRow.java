package com.rehoshi.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.rehoshi.model.Order;
import com.rehoshi.util.DateUtil;

public class OrderRow extends ExcelRow<Order>{
    @ExcelProperty(value = {"订单名称"},index = 1)
    private String name ;
    @ExcelProperty(value = {"订单状态"},index = 2)
    private String status ;
    @ExcelProperty(value = {"发货时间"},index = 3)
    private String createTime ;

    public OrderRow(Order model) {
        super(model);
        this.name = model.getName() ;
        this.status = model.getStatusStr() ;
        this.createTime = DateUtil.formatDateTime(model.getCreateTime()) ;
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
}
