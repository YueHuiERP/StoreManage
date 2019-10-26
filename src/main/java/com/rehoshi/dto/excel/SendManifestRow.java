package com.rehoshi.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.rehoshi.model.Manifest;
import lombok.Data;

@Data
public class SendManifestRow extends ExcelRow<Manifest> {

    @ExcelProperty(value = "订单名称",index = 1)
    private String name ;
    @ExcelProperty(value = "成品名称",index = 2)
    private String productName ;
    @ExcelProperty(value = "订单数量",index = 3)
    private Integer amount ;
    @ExcelProperty(value = "创建时间",index = 4)
    private String createTime ;

    public SendManifestRow(Manifest model) {
        super(model);
        this.name = model.getName() ;
        this.productName = model.getProduct().getName() ;
        this.amount = model.getAmount() ;
        this.createTime = model.getCreateTimeStr() ;
    }
}
