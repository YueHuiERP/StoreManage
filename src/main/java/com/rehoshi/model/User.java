package com.rehoshi.model;

import com.rehoshi.util.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用户
 */
@Data
public class User extends BaseModel {

    //角色
    public interface Role {
        int USER = 0; //用户
        int ADMIN = 1;//管理员
        int SUPER_ADMIN = 2;//超级管理员
    }
    //账号 账号是惟一的
    private String account;
    //密码 数据库保存的密码都是md5加密过得
    private String password;
    //名称
    private String name;
    //权限
    private Integer role;

    private String roleName;
    //登录时生成的凭证
    private String token;

    private String rePassword;

    private String oldPassword ;

    @DateTimeFormat(pattern = DateUtil.DATETIME_FORMAT)
    private Date createTime ;

    private String createTimeStr ;

    @DateTimeFormat(pattern = DateUtil.DATETIME_FORMAT)
    private Date lastLoginTime ;


    public void setRole(Integer role) {
        this.role = role;
        switch (getRole()) {
            case Role.USER:
                setRoleName("用户");
                break;
            case Role.ADMIN:
                setRoleName("管理员");
                break;
        }
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        setCreateTimeStr(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", createTime));
    }

    /**
     * 生成一个新的用户对象 会自动生成id
     *
     * @return
     */
    public static User newUser() {
        User user = new User();
        user.newId();
        return user;
    }
}
