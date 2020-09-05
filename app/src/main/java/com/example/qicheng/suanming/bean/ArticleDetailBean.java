package com.example.qicheng.suanming.bean;

public class ArticleDetailBean {

    /**
     * code : 200
     * msg : success
     * data : {"id":73,"title":"财运官运","name":"未来一个月我的财运如何？","category_id":4,"detail_pic":"https://dsj.zhouyi999.cn//uploads/","content":"<p style=\"text-indent: 28px;\"><span style=\";font-family:宋体;font-size:19px\"><span style=\"font-family:宋体\">在财运这条大道上，有人正财收入颇丰且平稳，有人偏财运十分好，随便买个彩票都能中。未来一个月你的财运又将如何？之前的投资是否有收益？周围朋友会给你带来合作？工作努力得到意外奖金？一测便见分晓。<\/span><\/span><\/p>","use_num":247424,"recomment_num":1282,"good_rate":"96.0","label":"20","price":"98.00","banner":"https://dsj.zhouyi999.cn//uploads/20200606/2020-06-06_1591431816_5edb52884ff21.png","ce_info":{"id":9,"uid":1,"name":"王","sex":1,"birthday":"2016-6-11","phone":"13520917230","created_at":"2020-06-10 17:57:32"},"coupon_amount":0,"pay_amount":"98.00"}
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
         * id : 73
         * title : 财运官运
         * name : 未来一个月我的财运如何？
         * category_id : 4
         * detail_pic : https://dsj.zhouyi999.cn//uploads/
         * content : <p style="text-indent: 28px;"><span style=";font-family:宋体;font-size:19px"><span style="font-family:宋体">在财运这条大道上，有人正财收入颇丰且平稳，有人偏财运十分好，随便买个彩票都能中。未来一个月你的财运又将如何？之前的投资是否有收益？周围朋友会给你带来合作？工作努力得到意外奖金？一测便见分晓。</span></span></p>
         * use_num : 247424
         * recomment_num : 1282
         * good_rate : 96.0
         * label : 20
         * price : 98.00
         * banner : https://dsj.zhouyi999.cn//uploads/20200606/2020-06-06_1591431816_5edb52884ff21.png
         * ce_info : {"id":9,"uid":1,"name":"王","sex":1,"birthday":"2016-6-11","phone":"13520917230","created_at":"2020-06-10 17:57:32"}
         * coupon_amount : 0
         * pay_amount : 98.00
         */

        private int id;
        private String title;
        private String name;
        private int category_id;
        private String detail_pic;
        private String content;
        private int use_num;
        private int recomment_num;
        private String good_rate;
        private String label;
        private String price;
        private String banner;
        private CeInfoBean ce_info;
        private int coupon_amount;
        private String pay_amount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getDetail_pic() {
            return detail_pic;
        }

        public void setDetail_pic(String detail_pic) {
            this.detail_pic = detail_pic;
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

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public CeInfoBean getCe_info() {
            return ce_info;
        }

        public void setCe_info(CeInfoBean ce_info) {
            this.ce_info = ce_info;
        }

        public int getCoupon_amount() {
            return coupon_amount;
        }

        public void setCoupon_amount(int coupon_amount) {
            this.coupon_amount = coupon_amount;
        }

        public String getPay_amount() {
            return pay_amount;
        }

        public void setPay_amount(String pay_amount) {
            this.pay_amount = pay_amount;
        }

        public static class CeInfoBean {
            /**
             * id : 9
             * uid : 1
             * name : 王
             * sex : 1
             * birthday : 2016-6-11
             * phone : 13520917230
             * created_at : 2020-06-10 17:57:32
             */

            private int id;
            private int uid;
            private String name;
            private int sex;
            private String birthday;
            private String phone;
            private String created_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }
        }
    }
}
