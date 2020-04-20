package com.rehoshi.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rehoshi.util.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class HoshiModel extends BaseModel{

    @DateTimeFormat(pattern = DateUtil.DATETIME_FORMAT)
    private Date createTime ;
    @TableField(exist = false)
    private String createTimeStr ;

    @DateTimeFormat(pattern = DateUtil.DATETIME_FORMAT)
    private Date updateTime ;
    @TableField(exist = false)
    private String updateTimeStr ;

    public String getCreateTimeStr() {
        createTimeStr = DateUtil.formatDateTime(createTime) ;
        return createTimeStr;
    }

    public String getUpdateTimeStr() {
        updateTimeStr = DateUtil.formatDateTime(updateTime) ;
        return updateTimeStr;
    }
}
