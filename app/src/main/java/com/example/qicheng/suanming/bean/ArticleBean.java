package com.example.qicheng.suanming.bean;

import java.util.List;

public class ArticleBean {

    /**
     * code : 200
     * msg : success
     * data : {"total_page":1,"list":[{"id":1,"name":"我什么时候才会结婚","thumb":"http://www.dashiji.cn:8100//uploads/20200505/2020-05-05_1588664021_5eb116d5b6f63.jpeg","content":"<p>婚姻是人生最重要的事情之一,在一段感情<\/p>","use_num":127219,"recomment_num":1689,"good_rate":"91.0","label":"新用户体验","price":"52.00"}]}
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
         * list : [{"id":1,"name":"我什么时候才会结婚","thumb":"http://www.dashiji.cn:8100//uploads/20200505/2020-05-05_1588664021_5eb116d5b6f63.jpeg","content":"<p>婚姻是人生最重要的事情之一,在一段感情<\/p>","use_num":127219,"recomment_num":1689,"good_rate":"91.0","label":"新用户体验","price":"52.00"}]
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
             * id : 1
             * name : 我什么时候才会结婚
             * thumb : http://www.dashiji.cn:8100//uploads/20200505/2020-05-05_1588664021_5eb116d5b6f63.jpeg
             * content : <p>婚姻是人生最重要的事情之一,在一段感情</p>
             * use_num : 127219
             * recomment_num : 1689
             * good_rate : 91.0
             * label : 新用户体验
             * price : 52.00
             */

            private int id;
            private String name;
            private String thumb;
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

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
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
}
