package com.rehoshi.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.rehoshi.util.CollectionUtil;
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
@TableName("product")
public class Product extends BaseModel {

    //产品的名称
    private String name;

    //产品的成分
    @TableField(exist = false)
    private List<ProductComposition> compositions;

    //创建时间
    @DateTimeFormat(pattern = DateUtil.DATETIME_FORMAT)
    @JsonFormat(pattern = DateUtil.DATETIME_FORMAT, timezone = "GMT+8")
    private Date createTime;
    @TableField(exist = false)
    private String createTimeStr;
    @TableField(exist = false)
    private String createTimeLabel;

    private Double specs;

    //打包费用
    private Double packFee;

    //成品数量
    private Integer amount;

    //成品发货数量
    @TableField(exist = false)
    private Integer sendAmount;

    //成品剩余数量
    @TableField(exist = false)
    private Integer remainAmount;

    //生产的商品原料
    private String gid;
    @TableField(exist = false)
    private Goods goods;

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

    public Double getSpecsValue() {
        if (getGoods() == null) {
            return 0d;
        } else {
            return getGoods().getSpecsValue();
        }
    }

    public ProductComposition getMainMaterial(){
       return CollectionUtil.first(getCompositions(), ProductComposition::isMain) ;
    }

    public ProductComposition getPackageMaterial(){
        return CollectionUtil.first(getCompositions(), ProductComposition::isPackage) ;
    }

    public Double getMainCost(){
        return CollectionUtil.aggregate(getCompositions(), (cops, sum) -> {
            if(cops.isMain()){
                return sum + cops.getCost();
            }else {
                return sum ;
            }
        }, 0d) ;
    }

    public Double getCost(){
        return CollectionUtil.aggregate(getCompositions(), (cops, sum) ->{
           return sum + cops.getCost() ;
        }, 0d) * getAmount() ;
    }
}
