package com.example.qicheng.suanming.bean;

public class WxPayExtBean {

    private String type;
    private String order_sn;

    public WxPayExtBean(String type, String order_sn) {
        this.type = type;
        this.order_sn = order_sn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }
}
