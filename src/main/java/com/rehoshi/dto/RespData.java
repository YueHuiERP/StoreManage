package com.rehoshi.dto;

/**
 * 接口返回的响应数据
 * @param <T>
 */
public class RespData<T> {
    interface Code{
       int SUCCESS = 200 ;
       int FAIL = 0 ;
       int TOKEN_TIMEOUT = 1 ;
    }

    //响应码
    public int code ;
    //响应消息
    public String msg ;
    //查询数据
    public T data ;

    public RespData<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public RespData<T> setData(T data) {
        this.data = data;
        return this ;
    }

    public RespData<T> setMsg(String msg) {
        this.msg = msg;
        return this ;
    }
    public RespData<T> success(){
        this.code = Code.SUCCESS ;
        return this ;
    }
    public RespData<T> fail(){
        this.code = Code.FAIL ;
        return this ;
    }
    public RespData<T> tokenTimeOut(){
        this.code = Code.TOKEN_TIMEOUT ;
        return this ;
    }

    public static <T>RespData<T> success(T data){
        return new RespData<T>().success().setData(data).setMsg("成功") ;
    }

    public static <T>RespData<T> fail(T data){
        return new RespData<T>().fail().setData(data).setMsg("失败") ;
    }
    public static <T>RespData<T> tokenTimeOut(T data){
        return new RespData<T>().tokenTimeOut().setData(data).setMsg("Token过期，请重新登录") ;
    }
}
