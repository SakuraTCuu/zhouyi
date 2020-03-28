package com.example.qicheng.suanming.bean;

public class MineBeiyongBean {
    private int rname_id;
    private int user_id;
    private String xing;
    private String ming;

    public MineBeiyongBean(int rid,String xing,String ming) {
        this.rname_id = rid;
        this.xing = xing;
        this.ming = ming;
    }

    public String getXing() {
        return xing;
    }

    public void setXing(String xing) {
        this.xing = xing;
    }

    public String getMing() {
        return ming;
    }

    public void setMing(String ming) {
        this.ming = ming;
    }

    public int getRname_id() {
        return rname_id;
    }

    public void setRname_id(int rname_id) {
        this.rname_id = rname_id;
    }
}
