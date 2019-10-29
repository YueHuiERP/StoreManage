package com.rehoshi.dto.search;

import lombok.Data;

/**
 * 封装商品查询参数
 */

@Data
public class GoodPageSearch extends PageSearch{

    private Integer type;

    private String name;

    private Integer minSpecs;

    private Boolean withSendAmount = false ;

    public String getName() {
        if (name==null){
            return "%%";
        }
      return "%"+name+"%";
    }

    public Integer getMinSpecs() {
        if(minSpecs == null){
            minSpecs = Integer.MIN_VALUE ;
        }
        return minSpecs;
    }

    public Boolean getWithSendAmount() {
        if(withSendAmount == null){
            withSendAmount = false ;
        }
        return withSendAmount;
    }
}
