package com.qicheng.zhouyi.bean;

import org.json.JSONObject;

public class MineOrderBean {

    private JSONObject userInfo;
    private String orderTitle; //标题
    private String orderCode;  //订单号
    private String orderTime;  //下单时间
    private String type;// 类型   八字详批， 大师起名
    private int payStatus; //订单状态 已支付 未支付

    public MineOrderBean(String type, int payStatus, String orderTitle, String orderCode, String orderTime, JSONObject userInfo) {
        this.type = type;
        this.payStatus = payStatus;
        this.orderTitle = orderTitle;
        this.orderCode = orderCode;
        this.orderTime = orderTime;
        this.userInfo = userInfo;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public JSONObject getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(JSONObject userInfo) {
        this.userInfo = userInfo;
    }
}
