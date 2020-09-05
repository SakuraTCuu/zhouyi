package com.example.qicheng.suanming.bean;

import java.util.List;

public class DashiInfoBean {

    /**
     * code : 200
     * msg : success
     * data : {"dashi":[{"id":1,"name":"测试","avator":"http://dsj.zhouyi999.cn//uploads/20200506/2020-05-06_1588730706_5eb21b5254a92.png","banner":"http://dsj.zhouyi999.cn//uploads/20200506/2020-05-06_1588730709_5eb21b55c2a90.png","introduce":"<p>测试测试测试<\/p>","skill":"风水,占星,梅花易数,紫微斗数","score":"5.0","level":"资深大师","order_num":364,"concern":433,"reply_time":2,"working_seniority":10,"join_time":"2020-05-16 10:12:36","sex":"男","label":"高级风水师","yelp":"<p>ces&nbsp;<\/p>","created_at":"2020-05-06 10:05:55","updated_at":"2020-05-16 10:12:36","index_display":1,"second_display":1},{"id":2,"name":"李国良","avator":"http://dsj.zhouyi999.cn//uploads/20200516/2020-05-16_1589593002_5ebf43aa530d8.png","banner":"http://dsj.zhouyi999.cn//uploads/20200516/2020-05-16_1589593018_5ebf43baf0103.png","introduce":"<p>50多年实战经验，深得易经手面相之精华，可通过手面相准确判断缘主的婚恋情感、事业财运、流年运势、学业考试、子女孕育、吉凶<\/p>","skill":"八字,六爻,梅花易数","score":"4.5","level":"资深大师","order_num":231,"concern":123,"reply_time":3,"working_seniority":10,"join_time":"2020-05-16 10:12:36","sex":"男","label":"精准高,认真负责,高级风水师","yelp":"<p>大师算的非常精准，耐心又精致，回复的很快，给我很大的信心和鼓励，希望像师父算的那样如愿以偿！<\/p>","created_at":"2020-05-16 09:38:55","updated_at":"2020-05-16 10:12:36","index_display":1,"second_display":1},{"id":3,"name":"徐小敏","avator":"http://dsj.zhouyi999.cn//uploads/20200516/2020-05-16_1589593203_5ebf447323b30.png","banner":"http://dsj.zhouyi999.cn//uploads/20200516/2020-05-16_1589593212_5ebf447cb5048.png","introduce":"<p>40年实战经验，擅长择吉日良辰，命卦合婚，八字精批风水改运求子<\/p>","skill":"起名,紫微斗数,奇门遁甲","score":"3.8","level":"高级大师","order_num":172,"concern":298,"reply_time":7,"working_seniority":8,"join_time":"2020-05-16 10:12:36","sex":"女","label":"精准高,认真负责","yelp":"<p>40年实战经验，擅长择吉日良辰，命卦合婚，八字精批风水改运求子<\/p>","created_at":"2020-05-16 09:41:25","updated_at":"2020-05-16 10:12:36","index_display":1,"second_display":1}],"comment":[{"name":"匿名003","avator":"http://dsj.zhouyi999.cn//uploads/20200516/2020-05-16_1589595741_5ebf4e5d7ea0c.png","product":null,"label":"正能量","score":"5.0","content":"已经来很多次了，老师人很好，讲的很细致，说的都中肯，强烈推荐","comment_time":"2020-05-31 00:00:00","created_at":"2020-05-16 10:22:50","releate_id":1,"ds_name":"测试","ds_score":"5.0","ds_avator":"http://dsj.zhouyi999.cn//uploads/20200506/2020-05-06_1588730706_5eb21b5254a92.png","ds_skill":"风水,占星,梅花易数,紫微斗数"},{"name":"匿名002","avator":"http://dsj.zhouyi999.cn//uploads/20200516/2020-05-16_1589595677_5ebf4e1d3ef72.png","product":null,"label":"精准度高,细心负责","score":"3.0","content":"算的很准，都对上了！","comment_time":"2020-05-26 00:00:00","created_at":"2020-05-16 10:21:40","releate_id":2,"ds_name":"李国良","ds_score":"4.5","ds_avator":"http://dsj.zhouyi999.cn//uploads/20200516/2020-05-16_1589593002_5ebf43aa530d8.png","ds_skill":"八字,六爻,梅花易数"},{"name":"匿名001","avator":"http://dsj.zhouyi999.cn//uploads/20200516/2020-05-16_1589595542_5ebf4d9677dfd.png","product":null,"label":"正能量,精准度高","score":"4.0","content":"大师算的非常精准，耐心又精致，回复的很快，给我很大的信心和鼓励，希望像师父算的那样如愿以偿！","comment_time":"2020-05-19 00:00:00","created_at":"2020-05-16 10:20:12","releate_id":3,"ds_name":"徐小敏","ds_score":"3.8","ds_avator":"http://dsj.zhouyi999.cn//uploads/20200516/2020-05-16_1589593203_5ebf447323b30.png","ds_skill":"起名,紫微斗数,奇门遁甲"}]}
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
        private List<DashiBean> dashi;
        private List<CommentBean> comment;

        public List<DashiBean> getDashi() {
            return dashi;
        }

        public void setDashi(List<DashiBean> dashi) {
            this.dashi = dashi;
        }

        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }

        public static class DashiBean {
            /**
             * id : 1
             * name : 测试
             * avator : http://dsj.zhouyi999.cn//uploads/20200506/2020-05-06_1588730706_5eb21b5254a92.png
             * banner : http://dsj.zhouyi999.cn//uploads/20200506/2020-05-06_1588730709_5eb21b55c2a90.png
             * introduce : <p>测试测试测试</p>
             * skill : 风水,占星,梅花易数,紫微斗数
             * score : 5.0
             * level : 资深大师
             * order_num : 364
             * concern : 433
             * reply_time : 2
             * working_seniority : 10
             * join_time : 2020-05-16 10:12:36
             * sex : 男
             * label : 高级风水师
             * yelp : <p>ces&nbsp;</p>
             * created_at : 2020-05-06 10:05:55
             * updated_at : 2020-05-16 10:12:36
             * index_display : 1
             * second_display : 1
             */

            private int id;
            private String name;
            private String avator;
            private String banner;
            private String introduce;
            private String skill;
            private String score;
            private String level;
            private int order_num;
            private int concern;
            private int reply_time;
            private int working_seniority;
            private String join_time;
            private String sex;
            private String label;
            private String yelp;
            private String created_at;
            private String updated_at;
            private int index_display;
            private int second_display;

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

            public String getBanner() {
                return banner;
            }

            public void setBanner(String banner) {
                this.banner = banner;
            }

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }

            public String getSkill() {
                return skill;
            }

            public void setSkill(String skill) {
                this.skill = skill;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public int getOrder_num() {
                return order_num;
            }

            public void setOrder_num(int order_num) {
                this.order_num = order_num;
            }

            public int getConcern() {
                return concern;
            }

            public void setConcern(int concern) {
                this.concern = concern;
            }

            public int getReply_time() {
                return reply_time;
            }

            public void setReply_time(int reply_time) {
                this.reply_time = reply_time;
            }

            public int getWorking_seniority() {
                return working_seniority;
            }

            public void setWorking_seniority(int working_seniority) {
                this.working_seniority = working_seniority;
            }

            public String getJoin_time() {
                return join_time;
            }

            public void setJoin_time(String join_time) {
                this.join_time = join_time;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getYelp() {
                return yelp;
            }

            public void setYelp(String yelp) {
                this.yelp = yelp;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public int getIndex_display() {
                return index_display;
            }

            public void setIndex_display(int index_display) {
                this.index_display = index_display;
            }

            public int getSecond_display() {
                return second_display;
            }

            public void setSecond_display(int second_display) {
                this.second_display = second_display;
            }
        }

        public static class CommentBean {
            /**
             * name : 匿名003
             * avator : http://dsj.zhouyi999.cn//uploads/20200516/2020-05-16_1589595741_5ebf4e5d7ea0c.png
             * product : null
             * label : 正能量
             * score : 5.0
             * content : 已经来很多次了，老师人很好，讲的很细致，说的都中肯，强烈推荐
             * comment_time : 2020-05-31 00:00:00
             * created_at : 2020-05-16 10:22:50
             * releate_id : 1
             * ds_name : 测试
             * ds_score : 5.0
             * ds_avator : http://dsj.zhouyi999.cn//uploads/20200506/2020-05-06_1588730706_5eb21b5254a92.png
             * ds_skill : 风水,占星,梅花易数,紫微斗数
             */

            private int id;
            private String name;
            private String avator;
            private String product;
            private String label;
            private String score;
            private String content;
            private String comment_time;
            private String created_at;
            private int releate_id;
            private int ds_id;
            private String ds_name;
            private String ds_score;
            private String ds_avator;
            private String ds_skill;

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

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public int getReleate_id() {
                return releate_id;
            }

            public void setReleate_id(int releate_id) {
                this.releate_id = releate_id;
            }

            public String getDs_name() {
                return ds_name;
            }

            public void setDs_name(String ds_name) {
                this.ds_name = ds_name;
            }

            public String getDs_score() {
                return ds_score;
            }

            public void setDs_score(String ds_score) {
                this.ds_score = ds_score;
            }

            public String getDs_avator() {
                return ds_avator;
            }

            public void setDs_avator(String ds_avator) {
                this.ds_avator = ds_avator;
            }

            public String getDs_skill() {
                return ds_skill;
            }

            public void setDs_skill(String ds_skill) {
                this.ds_skill = ds_skill;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getDs_id() {
                return ds_id;
            }

            public void setDs_id(int ds_id) {
                this.ds_id = ds_id;
            }
        }
    }
}
