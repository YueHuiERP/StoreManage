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
}
