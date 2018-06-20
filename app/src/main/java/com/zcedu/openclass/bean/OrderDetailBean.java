package com.zcedu.openclass.bean;

/**
 * 订单详情的bean
 * Created by cheng on 2018/5/12.
 */

public class OrderDetailBean {
    private int id;
    private String name;
    private int itemType;
    private OrderDataBean orderDataBean;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public OrderDataBean getOrderDataBean() {
        return orderDataBean;
    }

    public void setOrderDataBean(OrderDataBean orderDataBean) {
        this.orderDataBean = orderDataBean;
    }
}
