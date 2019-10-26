package com.rehoshi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rehoshi.util.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 产品 将原品打包之后的
 * 使用原料包材 将原品打包好
 */
@Data
public class Product extends BaseModel {

    //产品id
    private String id;

    //产品的名称
    private String name;

    //产品的成分
    private List<ProductComposition> compositions;

    //创建时间
    @DateTimeFormat(pattern = DateUtil.DATETIME_FORMAT)
    @JsonFormat(pattern = DateUtil.DATETIME_FORMAT, timezone = "GMT+8")
    private Date createTime;
    private String createTimeStr;
    private String createTimeLabel ;

    private Double specs;

    //打包费用
    private Double packFee;

    //成品数量
    private Integer amount;

    //成品发货数量
    private Integer sendAmount;

    //成品剩余数量
    private Integer remainAmount;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductComposition> getCompositions() {
        if (compositions == null) {
            compositions = new ArrayList<>();
        }
        return compositions;
    }

    public void setCompositions(List<ProductComposition> compositions) {
        this.compositions = compositions;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        setCreateTimeStr(DateUtil.formatDateTime(createTime));
        setCreateTimeLabel(DateUtil.formatDate("yyyy-MM-dd HH:mm", createTime));
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public Double getSpecs() {
        return specs;
    }

    public void setSpecs(Double specs) {
        this.specs = specs;
    }

    public Double getPackFee() {
        return packFee;
    }

    public void setPackFee(Double packFee) {
        this.packFee = packFee;
    }

    public Integer getAmount() {
        if (amount == null) {
            amount = 0;
        }
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getRemainAmount() {
        return getAmount() - getSendAmount();
    }

    public Integer getSendAmount() {
        if (sendAmount == null) {
            sendAmount = 0;
        }
        return sendAmount;
    }

    public void setSendAmount(Integer sendAmount) {
        this.sendAmount = sendAmount;
    }
}
