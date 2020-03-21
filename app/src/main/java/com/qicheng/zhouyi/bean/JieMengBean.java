package com.qicheng.zhouyi.bean;

/**
 * Created by Sakura on 2020-03-21 15:48
 */
public class JieMengBean {
    private String tempName;
    private int tempid;

    public JieMengBean(String tempName, int tempid) {
        this.tempName = tempName;
        this.tempid = tempid;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public int getTempid() {
        return tempid;
    }

    public void setTempid(int tempid) {
        this.tempid = tempid;
    }
}
