package com.example.qicheng.suanming.bean;

public class ChooseNameBean {

    private String name;
    private boolean isCollect;

    public ChooseNameBean() {
    }

    public ChooseNameBean(String name, boolean isCollect) {
        this.setName(name);
        this.setCollect(isCollect);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }
}
