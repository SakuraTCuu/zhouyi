package com.qicheng.zhouyi.bean;

public class MineOrderBean {

    private String orderTitle; //标题
    private String orderCode;  //订单号
    private String orderTime;  //下单时间

    public MineOrderBean(){

    }

    public MineOrderBean(String orderTitle, String orderCode, String orderTime) {
        this.orderTitle = orderTitle;
        this.orderCode = orderCode;
        this.orderTime = orderTime;
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
}
