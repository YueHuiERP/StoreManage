package com.rehoshi.model;

import lombok.Data;

/**
 * 物品类 包含
 * 商品 原料 包材
 */
@Data
public class Goods extends BaseModel {

    public interface Type {
        int GOODS = 0;
        int MATERIAL = 1;
        int PACKAGE_MATERIAL = 2;
    }

    //名称
    private String name;

    //类型 默认商品类型
    private Integer type;

    //商品规格 标签用于显示
    private String specs;

    //商品规格单位
    private String specsUnit;

    //img  保存图片相对地址
    private String img;

    //商品真正的规格
    private Double specsValue ;

    //发货量
    private Double sendAmount ;


    public Integer getType() {
        if (type == null) {
            type = Type.GOODS;
        }
        return type;
    }

    public void judgeSpecsValue(){
        Double val ;
        try {
            val = Double.valueOf(this.getSpecs()) ;
        }catch (Exception e){
            val = 1d ;
        }
        setSpecsValue(val);
    }
}
