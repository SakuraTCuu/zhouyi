package com.example.qicheng.suanming.common;

/* 管理用户信息*/
public  class UserManager {

    private static UserManager _instance;
    private static String user_id;

    public static UserManager getInstance(){
        if(_instance==null){
            _instance = new UserManager();
        }
        return _instance;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        _instance.user_id = user_id;
    }
}
