package com.rehoshi.model;

import com.rehoshi.util.DateUtil;
import lombok.Data;

import java.util.Date;

/**
 * 供应商
 */
@Data
public class Supplier extends BaseModel {
    private String name ;
    private String description ;
    private Date createTime ;
    private String createTimeStr ;

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        setCreateTimeStr(DateUtil.formatDateTime(createTime));
    }
}
