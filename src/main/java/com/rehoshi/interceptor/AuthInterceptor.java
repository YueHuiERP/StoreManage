package com.rehoshi.interceptor;

import com.rehoshi.dto.RespData;
import com.rehoshi.model.User;
import com.rehoshi.util.ContextUtil;
import com.rehoshi.util.JsonUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    //一天
    private static long TIME_OUT = 1000 * 60 * 60 * 24;

    private String[] ignoreURI = {"/", "/user/login"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        //判断是否是资源文件 *.* 这样的文件资源
        boolean isRes = requestURI.matches(".*?\\.[^/]*");
        if (!isRes) {
            boolean filter = true ;
            //判断请求是否是不需要Token的请求
            for (String s : ignoreURI) {
                if (requestURI.equalsIgnoreCase(s)) {
                    filter = false;
                    break;
                }
            }
            if(filter){
                User curUser = ContextUtil.getCurUser(request);
                boolean tokenTimeOut = true;
                if (curUser != null) {
                    long time = curUser.getLastLoginTime().getTime();
                    tokenTimeOut = (System.currentTimeMillis() - time) > TIME_OUT;
                }
                if (tokenTimeOut) {
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().print(JsonUtil.toJson(RespData.tokenTimeOut(false)));
                    response.getWriter().flush();
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

