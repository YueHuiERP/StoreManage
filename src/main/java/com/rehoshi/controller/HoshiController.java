package com.rehoshi.controller;

import com.rehoshi.config.PageConfig;
import com.rehoshi.dto.RespData;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Consumer;

public class HoshiController {

    @Autowired
    protected HttpServletRequest request ;

    protected <T> RespData<T> $(Consumer<RespData<T>> consumer){
        RespData<T> respData = RespData.fail(null) ;
        try {
            consumer.accept(respData);
        }catch (Exception e){
            e.printStackTrace();
            respData.success(false).setMsg(e.getMessage()); ;
        }
        return respData ;
    }

    protected PageConfig $page(){
        return PageConfig.get(request) ;
    }
}
