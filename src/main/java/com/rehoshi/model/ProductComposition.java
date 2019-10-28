package com.rehoshi.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rehoshi.util.CollectionUtil;
import lombok.Data;

/**
 * 产品组成原料
 */
@Data
@TableName("productcops")
public class ProductComposition extends BaseModel {
    //成分id
    private String id;
    //原品id
    private String sId;
    @TableField(exist = false)
    private Stock stock;
    //原品数量
    private Integer amount;
    //原品批次
    private String batch;

    //产品id
    private String pId;

    @TableField(exist = false)
    private Product product;

    //冗余成品数量
    private Integer pAmount;

    //规格允许用户修改
    private String specs;
    //规格单位
    private String specsUnit;
    //规格真正的值
    private Double specsValue;

    //待发货订单所需要的商品
    private String gId;
    @TableField(exist = false)
    private Goods goods;

    //所属订单
    private String oId;
    @TableField(exist = false)
    private Order order;

    //订单发货量
    @TableField(exist = false)
    private Double sendAmount ;


    public void judgeSpecsValue() {
        Double val;
        try {
            val = Double.valueOf(this.getSpecs());
        } catch (Exception e) {
            val = 1d;
        }
        setSpecsValue(val);
    }

    public Double getSendAmount() {
        if (sendAmount == null){
            sendAmount = 0d ;
        }
        return sendAmount;
    }

    public Integer getAmount() {
        if(amount == null){
            amount = 0 ;
        }
        return amount;
    }

    public Double getSpecsValue() {
        if(specsValue == null){
            specsValue = 1d ;
        }
        return specsValue;
    }

    public void setSendAmount(Double sendAmount) {
        this.sendAmount = sendAmount;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Integer getpAmount() {
        return pAmount;
    }

    public void setpAmount(Integer pAmount) {
        this.pAmount = pAmount;
    }

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    /**
     * 是否是主材料
     * @return
     */
    public Boolean isMain(){
        boolean isMain = false ;
        Stock stock = getStock();
        if(stock != null){
            Goods goods = stock.getGoods();
            if(goods != null){
                isMain = goods.getType() == Goods.Type.GOODS ;
            }
        }
        return isMain ;
    }

    public Boolean isPackage(){
        return !isMain() ;
    }

    public Double getCost(){
        return getStock() == null ? 0 : (getStock().getProductPrice() * getAmount() );
    }
}
