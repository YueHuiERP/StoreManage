package com.rehoshi.dto.search;

public class UserPageSearch {
    private String name ;
    private String userId ;
    private Integer role ;

    public String getName() {
        if(name == null){
            name = "" ;
        }
        return "%"+name+"%";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
