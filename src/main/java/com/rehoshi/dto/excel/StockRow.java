package com.rehoshi.dto.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.rehoshi.model.Stock;
import com.rehoshi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class StockRow extends ExcelRow<Stock> {

    @ExcelProperty(value = {"库存批次"}, index = 1)
    private String batch;
    @ExcelProperty(value = {"库存类型"}, index = 2)
    private String type;
    @ExcelProperty(value = {"数量"}, index = 3)
    private Integer amount;
    @ExcelProperty(value = {"剩余"}, index = 4)
    private String remain;
    @ExcelProperty(value = {"价格"}, index = 5)
    private Double price;
    @ExcelProperty(value = {"供应商"}, index = 6)
    private String provider;
    @ExcelProperty(value = {"时间"}, index = 7)
    private String time;

    public StockRow(Stock model) {
        super(model);
        this.batch = model.getBatch();
        this.type = model.getGoods().getName();
        this.amount = model.getAmount();
        this.remain = (model.getAmount() * model.getGoods().getSpecsValue() - model.getProductAmount() - model.getWasteAmount()) + model.getGoods().getSpecsUnit();
        this.price = model.getPrice();
        this.provider = model.getProvider();
        this.time = DateUtil.formatDateTime(model.getCreateTime());
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemain() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }
}
