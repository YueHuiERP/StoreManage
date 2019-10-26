package com.rehoshi.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.rehoshi.model.Product;
import com.rehoshi.model.ProductComposition;
import com.rehoshi.util.DateUtil;

public class ProductCopsRow extends ExcelRow<ProductComposition> {

    @ExcelProperty(value = {"成品名称"}, index = 1)
    private String productName ;
    @ExcelProperty(value = {"成品数量"}, index = 2)
    private Integer productAmount ;
    @ExcelProperty(value = {"成品剩余"}, index = 3)
    private Integer remainAmount ;
    @ExcelProperty(value = {"生产时间"}, index = 4)
    private String productTime ;
    @ExcelProperty(value = {"打包费用"}, index = 5)
    private Double pkgFee ;
    @ExcelProperty(value = {"原料名称"}, index = 6)
    private String copsName ;
    @ExcelProperty(value = {"原料批次"}, index = 7)
    private String batch ;
    @ExcelProperty(value = {"批次价格"}, index = 8)
    private Double price ;
    @ExcelProperty(value = {"原料数量"}, index = 9)
    private Integer copsAmount ;

    public ProductCopsRow(Product product, ProductComposition model) {
        super(model);
        this.productName = product.getName() ;
        this.productAmount = product.getAmount() ;
        this.remainAmount = product.getRemainAmount() ;
        this.productTime = DateUtil.formatDateTime(product.getCreateTime()) ;
        this.pkgFee = product.getPackFee() ;
        this.copsName = model.getStock().getName() ;
        this.batch = model.getStock().getBatch() ;
        this.price = model.getStock().getPrice() ;
        this.copsAmount = model.getAmount() ;
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

    public String getProductTime() {
        return productTime;
    }

    public void setProductTime(String productTime) {
        this.productTime = productTime;
    }

    public Double getPkgFee() {
        return pkgFee;
    }

    public void setPkgFee(Double pkgFee) {
        this.pkgFee = pkgFee;
    }

    public String getCopsName() {
        return copsName;
    }

    public void setCopsName(String copsName) {
        this.copsName = copsName;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCopsAmount() {
        return copsAmount;
    }

    public void setCopsAmount(Integer copsAmount) {
        this.copsAmount = copsAmount;
    }

    public Integer getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(Integer remainAmount) {
        this.remainAmount = remainAmount;
    }
}
