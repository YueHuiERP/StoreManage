package com.rehoshi.util;

import com.rehoshi.model.User;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ContextUtil {

    public static final String CUR_USER_KEY = "curUser";

    public static User getCurUser() {
        return getCurUser(((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest());
    }

    public static User getCurUser(HttpServletRequest request) {
        User user = null;
        if (request != null) {
            HttpSession session = request.getSession();
            if (session != null) {
                user = (User) session.getAttribute(CUR_USER_KEY);
            }
        }
        return user;
    }

    public static void setCurUser(HttpServletRequest request, User curUser) {
        if (request != null) {
            HttpSession session = request.getSession();
            if (session != null) {
                session.setAttribute(CUR_USER_KEY, curUser);
            }
        }
    }

    public static void removeCurUser(HttpServletRequest request) {
        removeAttr(request, CUR_USER_KEY);
    }

    public static void removeAttr(HttpServletRequest request, String attrName) {
        if (request != null) {
            HttpSession session = request.getSession();
            if (session != null) {
                session.removeAttribute(attrName);
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}
