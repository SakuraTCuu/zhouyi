package com.example.qicheng.suanming.bean;

public class UserCeSuanInfo {

    /**
     * code : 200
     *      * msg : 保存成功
     *      * data : {"info_id":9}
     */

    private boolean code;
    private String msg;
    private DataBean data;

    public boolean getCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * info_id : 9
         */

        private int info_id;

        public int getInfo_id() {
            return info_id;
        }

        public void setInfo_id(int info_id) {
            this.info_id = info_id;
        }
    }
}
