package com.example.qicheng.suanming.bean;

import java.io.Serializable;
import java.util.List;

public class DashiDetailBean {

    /**
     * code : 200
     * msg : success
     * data : {"dashi":{"id":3,"name":"李国良","avator":"http://www.dashiji.cn:8100//uploads/20200430/2020-04-30_1588223980_5eaa5fecea64d.jpeg","banner":"http://www.dashiji.cn:8100//uploads/20200430/2020-04-30_1588223987_5eaa5ff3dd169.jpg","introduce":"<p>李国良个人介绍10个字李国良个人介绍10个字<\/p>","skill":"八字,风水,起名","score":"5.0","level":1,"order_num":467,"concern":374,"reply_time":8,"working_seniority":9,"join_time":"2020-04-30 17:10:49","sex":2,"label":"精准高,认真负责,高级风水师","yelp":"<p>李国良小编点评10个字李国良小编点评10个字<\/p>","created_at":"2020-04-30 13:21:49","updated_at":"2020-04-30 17:10:49","index_display":1,"second_display":0,"is_concern":0},"project":[{"name":"婚恋情感","price":"188.00","thumb":"http://www.dashiji.cn:8100//uploads/20200504/2020-05-04_1588553961_5eaf68e923ba7.jpeg"}]}
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

    public static class DataBean implements Serializable {
        /**
         * dashi : {"id":3,"name":"李国良","avator":"http://www.dashiji.cn:8100//uploads/20200430/2020-04-30_1588223980_5eaa5fecea64d.jpeg","banner":"http://www.dashiji.cn:8100//uploads/20200430/2020-04-30_1588223987_5eaa5ff3dd169.jpg","introduce":"<p>李国良个人介绍10个字李国良个人介绍10个字<\/p>","skill":"八字,风水,起名","score":"5.0","level":1,"order_num":467,"concern":374,"reply_time":8,"working_seniority":9,"join_time":"2020-04-30 17:10:49","sex":2,"label":"精准高,认真负责,高级风水师","yelp":"<p>李国良小编点评10个字李国良小编点评10个字<\/p>","created_at":"2020-04-30 13:21:49","updated_at":"2020-04-30 17:10:49","index_display":1,"second_display":0,"is_concern":0}
         * project : [{"name":"婚恋情感","price":"188.00","thumb":"http://www.dashiji.cn:8100//uploads/20200504/2020-05-04_1588553961_5eaf68e923ba7.jpeg"}]
         */

        private DashiBean dashi;
        private List<ProjectBean> project;

        public DashiBean getDashi() {
            return dashi;
        }

        public void setDashi(DashiBean dashi) {
            this.dashi = dashi;
        }

        public List<ProjectBean> getProject() {
            return project;
        }

        public void setProject(List<ProjectBean> project) {
            this.project = project;
        }

        public static class DashiBean {
            /**
             * id : 3
             * name : 李国良
             * avator : http://www.dashiji.cn:8100//uploads/20200430/2020-04-30_1588223980_5eaa5fecea64d.jpeg
             * banner : http://www.dashiji.cn:8100//uploads/20200430/2020-04-30_1588223987_5eaa5ff3dd169.jpg
             * introduce : <p>李国良个人介绍10个字李国良个人介绍10个字</p>
             * skill : 八字,风水,起名
             * score : 5.0
             * level : 1
             * order_num : 467
             * concern : 374
             * reply_time : 8
             * working_seniority : 9
             * join_time : 2020-04-30 17:10:49
             * sex : 2
             * label : 精准高,认真负责,高级风水师
             * yelp : <p>李国良小编点评10个字李国良小编点评10个字</p>
             * created_at : 2020-04-30 13:21:49
             * updated_at : 2020-04-30 17:10:49
             * index_display : 1
             * second_display : 0
             * is_concern : 0
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
            private int is_concern;

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

            public int getIs_concern() {
                return is_concern;
            }

            public void setIs_concern(int is_concern) {
                this.is_concern = is_concern;
            }
        }

        public static class ProjectBean implements Serializable {
            /**
             * name : 婚恋情感
             * price : 188.00
             * thumb : http://www.dashiji.cn:8100//uploads/20200504/2020-05-04_1588553961_5eaf68e923ba7.jpeg
             */
            private int id;
            private String name;
            private String price;
            private String thumb;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
