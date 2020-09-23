package com.example.qicheng.suanming.bean;

import com.google.gson.annotations.SerializedName;

public class WxPayBean {

    /**
     * code : true
     * msg : success
     * data : {"appid":"wxf7bbdb9bdc782bb3","partnerid":"1511101771","prepayid":"wx151756309863134a9acc106817ec300000","timestamp":"1600163791","noncestr":"fRbq9kmrbncags1A","package":"Sign=WXPay","sign":"6E3A2FAC4C07AAFFB118890103F7908F","out_trade_no":"202009155f608fceba44b4327"}
     */

    private boolean code;
    private String msg;
    private DataBean data;

    public boolean isCode() {
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
         * appid : wxf7bbdb9bdc782bb3
         * partnerid : 1511101771
         * prepayid : wx151756309863134a9acc106817ec300000
         * timestamp : 1600163791
         * noncestr : fRbq9kmrbncags1A
         * package : Sign=WXPay
         * sign : 6E3A2FAC4C07AAFFB118890103F7908F
         * out_trade_no : 202009155f608fceba44b4327
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        private String timestamp;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String sign;
        private String out_trade_no;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }
    }
}
