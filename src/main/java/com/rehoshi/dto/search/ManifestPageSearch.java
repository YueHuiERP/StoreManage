package com.rehoshi.dto.search;

import lombok.Data;

/**
 * 订单分页数据
 */
@Data
public class ManifestPageSearch extends PageSearch {

    //订单的名称
    private String name ;
    //订单状态
    private Integer status ;

    public String getName() {
        if(name == null){
            name = "" ;
        }
        return "%"+name+"%";
    }
}
