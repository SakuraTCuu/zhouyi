package com.example.qicheng.suanming.bean;

public class WxPayBean {

    /**
     * code : 200
     * msg : success
     * data : {"appid":"wxf7bbdb9bdc782bb3","partnerid":"1511101771","prepayid":"wx26104314882440abe77d59f21231726800","timestamp":"1590460994","noncestr":"hlRv5EwM8xhbAns1","sign":"B6572F0ED1D2BB61E27983E6E6D1C6C9","pkg":"Sign=WXPay"}
     */

    private int code;
    private String msg;
    private DataBean data;

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
         * prepayid : wx26104314882440abe77d59f21231726800
         * timestamp : 1590460994
         * noncestr : hlRv5EwM8xhbAns1
         * sign : B6572F0ED1D2BB61E27983E6E6D1C6C9
         * pkg : Sign=WXPay
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        private String timestamp;
        private String noncestr;
        private String sign;
        private String pkg;

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

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPkg() {
            return pkg;
        }

        public void setPkg(String pkg) {
            this.pkg = pkg;
        }
    }
}
