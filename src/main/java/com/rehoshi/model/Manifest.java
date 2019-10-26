package com.rehoshi.model;

import com.rehoshi.util.CollectionUtil;
import com.rehoshi.util.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 发货清单
 * 用来替代之前老的Order类
 */
@Data
public class Manifest extends BaseModel {
    /**
     * 订单状态
     */
    public interface Status {
        //待发货
        int WAIT_SEND = 0;
        //已发货
        int HAS_SENT = 1;
    }

    //订单名称
    private String name;

    //成品id
    private String pid;

    //成品
    private Product product;

    //订单数量
    private Integer amount;

    //状态
    private Integer status;

    private String parentId;
    //父订单
    private Manifest parent;

    //订单子项目 表示该订单的已发货订单
    private List<Manifest> subManifests = new ArrayList<>();

    //订单对应的商品
    private String gid;
    private Goods goods;

    //创建时间
    @DateTimeFormat(pattern = DateUtil.DATETIME_FORMAT)
    private Date createTime;
    private String createTimeStr;

    //订单发货量
    private Double sendAmount;

    private Double remainAmount;

    private Boolean visible = true;

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        setCreateTimeStr(DateUtil.formatDateTime(this.createTime));
    }

    public String getStatusStr() {
        return getStatus() == Manifest.Status.WAIT_SEND ? "待发货" : "已发货";
    }

    public void newChildren() {
        CollectionUtil.foreach(getSubManifests(), child -> {
            child.newId();
            child.setCreateTime(getCreateTime());
            child.setParentId(getId());
        });
    }

    public Integer getAmount() {
        return amount == null ? 0 : amount;
    }

    public Double getSendAmount() {
        return sendAmount == null ? 0 : sendAmount;
    }

    public Double getRemainAmount() {
        return getAmount() - getSendAmount();
    }

    public Manifest newChild(){
        Manifest child = new Manifest() ;
        child.setName(getName());
        child.setPid(getPid());
        child.setAmount(getAmount());
        child.setStatus(getStatus());
        child.setVisible(false);
        return child ;
    }
}
