package com.example.qicheng.suanming.bean;

/**
 * Created by Sakura on 2020-03-25 15:15
 */
public class DaShiKeFuBean {
    private String kefu_wx;
    private String kefu_img_url;
    private String dashi_name;
    private String dashi_img;
    private String dashi_desc;

    public DaShiKeFuBean(String kefu_wx, String kefu_img_url, String dashi_name, String dashi_img, String dashi_desc) {
        this.kefu_wx = kefu_wx;
        this.kefu_img_url = kefu_img_url;
        this.dashi_name = dashi_name;
        this.dashi_img = dashi_img;
        this.dashi_desc = dashi_desc;
    }

    public String getKefu_wx() {
        return kefu_wx;
    }

    public void setKefu_wx(String kefu_wx) {
        this.kefu_wx = kefu_wx;
    }

    public String getDashi_name() {
        return dashi_name;
    }

    public void setDashi_name(String dashi_name) {
        this.dashi_name = dashi_name;
    }

    public String getDashi_img() {
        return dashi_img;
    }

    public void setDashi_img(String dashi_img) {
        this.dashi_img = dashi_img;
    }

    public String getDashi_desc() {
        return dashi_desc;
    }

    public void setDashi_desc(String dashi_desc) {
        this.dashi_desc = dashi_desc;
    }

    public String getKefu_img_url() {
        return kefu_img_url;
    }

    public void setKefu_img_url(String kefu_img_url) {
        this.kefu_img_url = kefu_img_url;
    }
}
