package com.qicheng.zhouyi.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sakura on 2020-03-20 10:20
 */
public class UserModel {

// "data":{"user_id":52,"head_img":
// "http:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/efBHfxblExDkT2icpUHwftEApV0pFAVgO2tWiaUL11NnqSW7C4icscBTrHSCY8wJBbTic2sdzJpB2rNowKaz5yQKQA\/132",
// "nick_name":"Violet Evergarden","gender":1,"phone":null,"birthday":"","status":0}}

    private String user_id;
    private String head_img;
    private String nick_name;
    private String gender;

    public UserModel(String user_id, String head_img, String nick_name, String gender) {
        this.user_id = user_id;
        this.head_img = head_img;
        this.nick_name = nick_name;
        this.gender = gender;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String toString() {
        JSONObject data = new JSONObject();
        try {
            data.put("user_id",user_id);
            data.put("head_img",head_img);
            data.put("nick_name",nick_name);
            data.put("gender",gender);
           return  data.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user_id + "_" + head_img + "_" + nick_name + "_" + gender;
    }
}
