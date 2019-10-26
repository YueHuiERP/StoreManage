package com.rehoshi.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.rehoshi.model.Waste;
import com.rehoshi.util.DateUtil;

public class WasteRow extends ExcelRow<Waste> {

    @ExcelProperty(value = {"库存名称"},index = 1)
    private String stockName;
    @ExcelProperty(value = {"库存批次"},index = 2)
    private String batch ;
    @ExcelProperty(value = {"损耗数量"},index = 3)
    private Double amount ;
    @ExcelProperty(value = {"审核人员"},index = 4)
    private String creatorName  ;
    @ExcelProperty(value = {"时间"},index = 5)
    private String date ;

    public WasteRow(Waste model) {
        super(model);
        this.stockName = model.getStock().getName() ;
        this.batch = model.getStock().getBatch() ;
        this.amount = model.getWeight() ;
        this.creatorName = model.getCreator().getName() ;
        this.date = DateUtil.formatDateTime(model.getCreateTime()) ;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}
