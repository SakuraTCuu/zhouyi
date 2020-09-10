package com.example.qicheng.suanming.bean;

import java.util.List;

public class DashiDetailCommentBean {

    /**
     * code : 200
     * msg : success
     * data : {"total_page":1,"list":[{"id":18,"name":"燃尽","avator":"https://dsj.zhouyi999.cn//uploads/20200526/2020-05-26_1590474852_5eccb864e254c.jpg","product":"事业财运","label":"精准度高,细心负责","score":"4.9","content":"用户超时未评价，默认好评！","comment_time":"2016-05-27 00:00:00"},{"id":17,"name":"2645326","avator":"https://dsj.zhouyi999.cn//uploads/20200526/2020-05-26_1590474742_5eccb7f604527.jpg","product":"八字合婚","label":"精准度高,细致专业,细心负责","score":"4.9","content":"手感确实不错，入手冰凉","comment_time":"2016-05-31 00:00:00"},{"id":16,"name":"颐养天年","avator":"https://dsj.zhouyi999.cn//uploads/20200526/2020-05-26_1590474676_5eccb7b4e2a8d.jpg","product":"八字合婚","label":"正能量,精准度高","score":"4.9","content":"收到了之后，就立即打开，很喜欢，很精致，凉凉的手感，感恩，希望以后平安如意、顺遂和美！","comment_time":"2020-05-26 00:00:00"}]}
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
         * total_page : 1
         * list : [{"id":18,"name":"燃尽","avator":"https://dsj.zhouyi999.cn//uploads/20200526/2020-05-26_1590474852_5eccb864e254c.jpg","product":"事业财运","label":"精准度高,细心负责","score":"4.9","content":"用户超时未评价，默认好评！","comment_time":"2016-05-27 00:00:00"},{"id":17,"name":"2645326","avator":"https://dsj.zhouyi999.cn//uploads/20200526/2020-05-26_1590474742_5eccb7f604527.jpg","product":"八字合婚","label":"精准度高,细致专业,细心负责","score":"4.9","content":"手感确实不错，入手冰凉","comment_time":"2016-05-31 00:00:00"},{"id":16,"name":"颐养天年","avator":"https://dsj.zhouyi999.cn//uploads/20200526/2020-05-26_1590474676_5eccb7b4e2a8d.jpg","product":"八字合婚","label":"正能量,精准度高","score":"4.9","content":"收到了之后，就立即打开，很喜欢，很精致，凉凉的手感，感恩，希望以后平安如意、顺遂和美！","comment_time":"2020-05-26 00:00:00"}]
         */

        private int total_page;
        private List<ListBean> list;

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 18
             * name : 燃尽
             * avator : https://dsj.zhouyi999.cn//uploads/20200526/2020-05-26_1590474852_5eccb864e254c.jpg
             * product : 事业财运
             * label : 精准度高,细心负责
             * score : 4.9
             * content : 用户超时未评价，默认好评！
             * comment_time : 2016-05-27 00:00:00
             */

            private int id;
            private String name;
            private String avator;
            private String product;
            private String label;
            private String score;
            private String content;
            private String comment_time;

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

            public String getAvator() {
                return avator;
            }

            public void setAvator(String avator) {
                this.avator = avator;
            }

            public String getProduct() {
                return product;
            }

            public void setProduct(String product) {
                this.product = product;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getComment_time() {
                return comment_time;
            }

            public void setComment_time(String comment_time) {
                this.comment_time = comment_time;
            }
        }
    }
}
