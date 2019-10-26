package com.rehoshi.model;

import com.rehoshi.util.CollectionUtil;
import com.rehoshi.util.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单
 */
@Deprecated
@Data
public class Order extends BaseModel {
    /**
     * 订单状态
     */
    public interface Status {
        //待发货
        int WAIT_SEND = 0;
        //已发货
        int HAS_SENT = 1;
    }

    //订单编号
    private String serial;

    //订单名称
    private String name;

    //库存商品id
    private String pId;

    //成品
    private Product product;

    //订单数量
    private Integer amount;

    //状态
    private Integer status;

    private String parentId;
    //父订单
    private Order parent;
    //订单子项目 已发货订单
    private List<Order> subOrders = new ArrayList<>();

    //待发货订单调用库存
    private List<ProductComposition> items = new ArrayList<>() ;

    //创建时间
    @DateTimeFormat(pattern = DateUtil.DATETIME_FORMAT)
    private Date createTime;
    private String createTimeStr;


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        setCreateTimeStr(DateUtil.formatDateTime(createTime));
    }


    public void setItems(List<ProductComposition> items) {
        this.items = items;
    }

    public String getStatusStr() {
        return getStatus() == Status.WAIT_SEND ? "待发货" : "已发货";
    }
    public void newChildren() {
        CollectionUtil.foreach(getSubOrders(), child -> {
            child.newId();
            child.setCreateTime(getCreateTime());
            child.setParentId(getId());
        });
    }

    public void newItems() {
        CollectionUtil.foreach(getItems(), item->{
            item.newId();
            item.setoId(getId());
        });
    }
}
