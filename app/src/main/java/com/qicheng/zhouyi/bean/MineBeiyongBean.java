package com.qicheng.zhouyi.bean;

public class MineBeiyongBean {
    private String userName;

    public MineBeiyongBean() {
        userName = "";
    }

    public MineBeiyongBean(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
