package com.example.qicheng.suanming.bean;

import java.util.List;

public class DashiListBean {
    /**
     * code : 200
     * msg : success
     * data : {"total_page":1,"list":[{"id":4,"name":"大师1","avator":"http://www.dashiji.cn:8100//uploads/20200430/2020-04-30_1588231060_5eaa7b94430c2.jpeg","banner":"20200430/2020-04-30_1588226716_5eaa6a9cbff70.jpg","introduce":"<p>大师1个人介绍大师1个人介绍大师1个人介绍<\/p>","skill":"八字,起名,测字,手面相","score":"5.0","level":3,"order_num":372,"concern":457,"reply_time":8,"working_seniority":4,"join_time":"2020-04-30 17:11:48","sex":2,"label":"精准高,认真负责,高级风水师","yelp":"<p>大师1小编点评大师1小编点评大师1小编点评<\/p>","created_at":"2020-04-30 14:06:01","updated_at":"2020-04-30 17:11:48","index_display":0,"second_display":1},{"id":3,"name":"李国良","avator":"http://www.dashiji.cn:8100//uploads/20200430/2020-04-30_1588223980_5eaa5fecea64d.jpeg","banner":"20200430/2020-04-30_1588223987_5eaa5ff3dd169.jpg","introduce":"<p>李国良个人介绍10个字李国良个人介绍10个字<\/p>","skill":"八字,风水,起名","score":"5.0","level":1,"order_num":467,"concern":374,"reply_time":8,"working_seniority":9,"join_time":"2020-04-30 17:10:49","sex":2,"label":"精准高,认真负责,高级风水师","yelp":"<p>李国良小编点评10个字李国良小编点评10个字<\/p>","created_at":"2020-04-30 13:21:49","updated_at":"2020-04-30 17:10:49","index_display":1,"second_display":0}]}
     */

    private String code;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
         * list : [{"id":4,"name":"大师1","avator":"http://www.dashiji.cn:8100//uploads/20200430/2020-04-30_1588231060_5eaa7b94430c2.jpeg","banner":"20200430/2020-04-30_1588226716_5eaa6a9cbff70.jpg","introduce":"<p>大师1个人介绍大师1个人介绍大师1个人介绍<\/p>","skill":"八字,起名,测字,手面相","score":"5.0","level":3,"order_num":372,"concern":457,"reply_time":8,"working_seniority":4,"join_time":"2020-04-30 17:11:48","sex":2,"label":"精准高,认真负责,高级风水师","yelp":"<p>大师1小编点评大师1小编点评大师1小编点评<\/p>","created_at":"2020-04-30 14:06:01","updated_at":"2020-04-30 17:11:48","index_display":0,"second_display":1},{"id":3,"name":"李国良","avator":"http://www.dashiji.cn:8100//uploads/20200430/2020-04-30_1588223980_5eaa5fecea64d.jpeg","banner":"20200430/2020-04-30_1588223987_5eaa5ff3dd169.jpg","introduce":"<p>李国良个人介绍10个字李国良个人介绍10个字<\/p>","skill":"八字,风水,起名","score":"5.0","level":1,"order_num":467,"concern":374,"reply_time":8,"working_seniority":9,"join_time":"2020-04-30 17:10:49","sex":2,"label":"精准高,认真负责,高级风水师","yelp":"<p>李国良小编点评10个字李国良小编点评10个字<\/p>","created_at":"2020-04-30 13:21:49","updated_at":"2020-04-30 17:10:49","index_display":1,"second_display":0}]
         */

        private int total_page;
        private List<DashiInfoBean.DataBean.DashiBean> list;

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }

        public List<DashiInfoBean.DataBean.DashiBean> getList() {
            return list;
        }

        public void setList(List<DashiInfoBean.DataBean.DashiBean> list) {
            this.list = list;
        }

    }
}
