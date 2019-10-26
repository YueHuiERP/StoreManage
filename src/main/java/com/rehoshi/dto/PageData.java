package com.rehoshi.dto;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 分页数据 符合 lay-ui格式
 * @param <T>
 */
public class PageData<T> {
    public int code ;
    public List<T> data ;
    public String msg ;
    public long count ;

    public PageData(List<T> data){
        this(new PageInfo<>(data)) ;
    }

    public PageData(PageInfo<T> pageInfo){
        this.code = 0 ;
        this.data = pageInfo.getList() ;
        if(this.data == null || this.data.isEmpty()){
            this.msg = "暂无数据" ;
        }else {
            this.msg = "查询成功" ;
        }
        this.count = pageInfo.getTotal() ;
    }
}
