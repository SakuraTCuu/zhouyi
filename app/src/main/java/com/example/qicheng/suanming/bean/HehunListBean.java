package com.example.qicheng.suanming.bean;

/**
 * Created by Sakura on 2020-01-14 15:33
 */
public class HehunListBean {

    private String title;
    private String content;


    public HehunListBean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
