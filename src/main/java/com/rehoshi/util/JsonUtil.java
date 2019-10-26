package com.rehoshi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Json序列化的工具类
 */
public class JsonUtil {


    public static <T>T fromJson(String json, Class<T> cls){
        ObjectMapper objectMapper = new ObjectMapper();
        T obj = null ;
        try {
            obj = objectMapper.readValue(json, cls) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj ;
    }

    public static String toJson(Object obj){
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null ;
        try {
            json = objectMapper.writeValueAsString(obj) ;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json ;
    }

}
