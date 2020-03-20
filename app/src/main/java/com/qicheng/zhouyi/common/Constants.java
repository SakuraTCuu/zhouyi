package com.qicheng.zhouyi.common;

import com.qicheng.zhouyi.bean.UserModel;
import com.qicheng.zhouyi.ui.MyApplication;

import com.qicheng.zhouyi.utils.SPUtils;

public class Constants {

    public static final String Filename = "zhouyi";       //shaperpager保存文件

    //    public static String userId = "48"; //用户id
    public static boolean isLogin = false;
    public static UserModel userInfo;

    public static String getUid() {
        return (String) SPUtils.get(MyApplication.getInstance(), "uid", "");
    }

    public static void removeUserInfo(){
       if(SPUtils.contains(MyApplication.getInstance(),"uid")){
           SPUtils.remove(MyApplication.getInstance(),"uid");
       }
        if(SPUtils.contains(MyApplication.getInstance(),"userInfo")){
            SPUtils.remove(MyApplication.getInstance(),"userInfo");
        }
    }
    public static String getUserInfo() {
        return (String) SPUtils.get(MyApplication.getInstance(), "userInfo", "");
    }

    public static void saveData() {
        isLogin = true;
        if(userInfo !=null){
            saveUserId(userInfo.getUser_id());
            saveUserInfo(userInfo);
        }else{

        }

    }

    public static void saveUserId(String user_id) {
//        if(SPUtils.contains(MyApplication.getInstance(),"uid")){
//            //已经存在的就删了
//
//        }
        SPUtils.put(MyApplication.getInstance(), "uid", user_id);
    }

    public static void saveUserInfo(UserModel uModel) {
        SPUtils.put(MyApplication.getInstance(), "userInfo", uModel.toString());
    }

    // key
    public static final String shanyanKey = "BCtJkQBB";
    //      appid
    public static final String shanyanAppId = "3c3LQ33b";


    public static class getApi {
        public static final String URL = "http://app.zhouyi999.cn/";
//        public static final String URL = "http://app.nyqicheng.cn/"; //临时域名

        // 起名接口
        public static final String QIMING = URL + "index/names/addQiMing";

        // 八字精批接口
        public static final String BAZI = URL + "index/bazi/bzjp";

        // 获取验证码
        public static final String GETCODE = URL + "index/login/sendPhoneCode";

        //验证码登录
        public static final String CODELOGIN = URL + "index/login/codeLogin";

        //闪验登录
        public static final String SYLOGIN = URL + "index/login/flash_query";

        //微信登录
        public static final String WXLOGIN = URL + "index/login/wxLogin";

        //获取用户信息
        public static final String GETUSERINFO = URL + "index/user/userInfo";

        //修改用户信息
        public static final String UPDATEUSERINFO = URL + "index/user/updateUserInfo";

        //修改用户头像
        public static final String UPDATEUSERICON = URL + "index/user/updateUserHeadImg";

        //添加用户反馈
        public static final String USERREPORT = URL + "index/user/userReport";

        //添加用户收藏
        public static final String NAMECOLLECT = URL + "index/names/collentName";

        //删除用户收藏
        public static final String NAMECOLLECTDEL = URL + "index/names/collectNameDel";

        //获取 小吉名 大吉名列表  未解锁跳转支付，解锁展示
        public static final String GETJIMING = URL + "index/order/getJiMing";

        //获取备用姓名
        public static final String GETCOLLECTNAME = URL + "index/names/myCollectName";

        //获取订单列表
        public static final String GETORDERLIST = URL + "index/order/orderList";

        //八字精批 接口
//        public static final String BAZIJINGPI = URL + "index/order/bazijp";
//        public static final String BAZIJINGPI = URL + "index/order/test_bzjp";
        public static final String GETH5URL = URL + "index/order/bzh5_url";
    }
}