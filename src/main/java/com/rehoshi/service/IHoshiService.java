package com.rehoshi.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IHoshiService<T> extends IService<T> {
    default List<T> listBySearch(String key){
        return this.listBySearch(key, null);
    }

    List<T> listBySearch(String key, Object params);
}
