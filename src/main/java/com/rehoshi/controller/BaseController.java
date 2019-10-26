package com.rehoshi.controller;

import com.rehoshi.util.JsonUtil;

public class BaseController {
    protected String $(Object object){
        return JsonUtil.toJson(object) ;
    }
}
