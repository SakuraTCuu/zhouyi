package com.example.qicheng.suanming.bean;

import java.util.List;

public class VIPHomeDataBean {

    /**
     * code : 200
     * msg : success
     * data : [{"id":1,"name":"我什么时候才会结婚","index_pic":"http://www.dashiji.cn:8100//uploads/20200505/2020-05-05_1588664002_5eb116c21fcc9.jpg","content":"<p>婚姻是人生最重要的事情之一,在一段感情<\/p>","use_num":127219,"recomment_num":1689,"good_rate":"91.0","label":"新用户体验","price":"52.00"}]
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
         * name : 我什么时候才会结婚
         * index_pic : http://www.dashiji.cn:8100//uploads/20200505/2020-05-05_1588664002_5eb116c21fcc9.jpg
         * content : <p>婚姻是人生最重要的事情之一,在一段感情</p>
         * use_num : 127219
         * recomment_num : 1689
         * good_rate : 91.0
         * label : 新用户体验
         * price : 52.00
         */

        private int id;
        private String name;
        private String index_pic;
        private String content;
        private int use_num;
        private int recomment_num;
        private String good_rate;
        private String label;
        private String price;

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

        public String getIndex_pic() {
            return index_pic;
        }

        public void setIndex_pic(String index_pic) {
            this.index_pic = index_pic;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getUse_num() {
            return use_num;
        }

        public void setUse_num(int use_num) {
            this.use_num = use_num;
        }

        public int getRecomment_num() {
            return recomment_num;
        }

        public void setRecomment_num(int recomment_num) {
            this.recomment_num = recomment_num;
        }

        public String getGood_rate() {
            return good_rate;
        }

        public void setGood_rate(String good_rate) {
            this.good_rate = good_rate;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
