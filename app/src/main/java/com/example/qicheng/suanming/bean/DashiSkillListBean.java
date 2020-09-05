package com.example.qicheng.suanming.bean;

import java.util.List;

public class DashiSkillListBean {

    /**
     * code : 200
     * msg : success
     * data : [{"id":1,"name":"婚恋情感"},{"id":2,"name":"命运详批"},{"id":3,"name":"事业财运"},{"id":4,"name":"风水调理"},{"id":5,"name":"选时择日"},{"id":6,"name":"周公解梦"},{"id":7,"name":"今年运势"},{"id":8,"name":"起名改名"},{"id":9,"name":"月老姻缘"},{"id":10,"name":"占卜断事"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 婚恋情感
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
