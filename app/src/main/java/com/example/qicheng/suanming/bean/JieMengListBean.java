package com.example.qicheng.suanming.bean;

import java.io.Serializable;

/**
 * Created by Sakura on 2020-03-21 16:41
 */
public class JieMengListBean  implements Serializable {
    private String title;
    private int id;

    public JieMengListBean(String title,int id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
