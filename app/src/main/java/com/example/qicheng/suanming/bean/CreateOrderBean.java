package com.example.qicheng.suanming.bean;

public class CreateOrderBean {

    /**
     * code : 200
     * msg : 创建订单成功
     * data : {"order_no":"202005281747587190191813","created_at":"2020-05-28 17:47:58"}
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
         * order_no : 202005281747587190191813
         * created_at : 2020-05-28 17:47:58
         */

        private String order_no;
        private String created_at;

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
