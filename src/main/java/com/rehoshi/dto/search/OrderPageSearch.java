package com.rehoshi.dto.search;

import com.rehoshi.util.DateUtil;

import java.util.Date;

public class OrderPageSearch {
    private String name ;//订单名称
    private Date startTime ;//订单开始时间
    private Date endTime ; //订单结束时间
    private Integer status; // 订单状态

    public String getName() {
        if(name == null){
            name = "" ;
        }
        return "%"+name+"%";
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        if(startTime == null){
            startTime = new Date(0) ;
        }
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        if (endTime == null){
            endTime = DateUtil.tomorrow();
        }
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
