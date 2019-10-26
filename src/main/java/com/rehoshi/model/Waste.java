package com.rehoshi.model;

import com.rehoshi.util.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 损耗 生产产品过程中的消耗
 */
@Data
public class Waste extends BaseModel{

    //损耗的商品id
    private String sId ;
    private Stock stock ;

    //损耗的商品重量 单位斤
    private Double weight ;

    //损耗创建日期
    @DateTimeFormat(pattern = DateUtil.DATETIME_FORMAT)
    private Date createTime ;

    private String createTimeStr ;

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        setCreateTimeStr(DateUtil.formatDateTime(createTime));
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }
}
