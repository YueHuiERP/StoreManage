package com.rehoshi.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.rehoshi.model.Manifest;
import lombok.Data;

@Data
public class PrepareManifestRow extends ExcelRow<Manifest> {

    @ExcelProperty(value = "订单名称", index = 1)
    private String name ;
    @ExcelProperty(value = "订单数量", index = 2)
    private Integer amount ;
    @ExcelProperty(value = "剩余数量", index = 3)
    private  Double remainAmount ;
    @ExcelProperty(value = "创建时间", index = 4)
    private String createTime ;

    public PrepareManifestRow(Manifest model) {
        super(model);
        this.name = model.getName() ;
        this.remainAmount = model.getRemainAmount() ;
        this.amount = model.getAmount() ;
        this.createTime = model.getCreateTimeStr() ;
    }
}
