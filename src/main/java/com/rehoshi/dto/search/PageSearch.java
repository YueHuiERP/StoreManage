package com.rehoshi.dto.search;

import com.rehoshi.util.DateUtil;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * 分页查找数据条件对象
 */
@Data
public class PageSearch {
    //开始页码
    private int pageIndex ;
    //结束页码
    private int pageSize ;
    //开始时间
    private Date startTime ;
    //结束时间
    private Date endTime ;

    public Date getStartTime() {
        if(startTime == null){
            startTime = new Date(0) ;
        }
        return startTime;
    }

    public Date getEndTime() {
        if (endTime == null){
            endTime = DateUtil.todayEnd();
        }
        return endTime;
    }
}
