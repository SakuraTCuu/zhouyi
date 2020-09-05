package com.example.qicheng.suanming.bean;

import java.io.Serializable;
import java.util.List;

public class VIPListDataBean {

    /**
     * code : 200
     * msg : success
     * data : [{"id":3,"name":"恋爱情感 ","thumb":"http://www.dashiji.cn:8100//uploads/20200505/2020-05-05_1588660641_5eb109a1e59cc.jpeg"},{"id":4,"name":"财运官运","thumb":"http://www.dashiji.cn:8100//uploads/20200505/2020-05-05_1588660641_5eb109a1e59cc.jpeg"},{"id":5,"name":"职场事业","thumb":"http://www.dashiji.cn:8100//uploads/20200505/2020-05-05_1588660641_5eb109a1e59cc.jpeg"}]
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

    public static class DataBean implements Serializable {
        /**
         * id : 3
         * name : 恋爱情感
         * thumb : http://www.dashiji.cn:8100//uploads/20200505/2020-05-05_1588660641_5eb109a1e59cc.jpeg
         */

        private int id;
        private String name;
        private String thumb;

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

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }
}
