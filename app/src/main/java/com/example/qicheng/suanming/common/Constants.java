package com.example.qicheng.suanming.common;

import android.util.Log;

import com.example.qicheng.suanming.bean.DaShiKeFuBean;
import com.example.qicheng.suanming.bean.UserModel;
import com.example.qicheng.suanming.ui.MyApplication;

import com.example.qicheng.suanming.utils.MD5Utils;
import com.example.qicheng.suanming.utils.MapUtils;
import com.example.qicheng.suanming.utils.SPUtils;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Constants {

    public static final String Filename = "zhouyi";       //shaperpager保存文件

    //    public static String userId = "48"; //用户id
    public static boolean isLogin = false;
    public static String os_type = "";
    public static UserModel userInfo;
    public static String session_key = "";
    public static DaShiKeFuBean kefuInfo;
    public static String localUrl;
    public static String nickName;

    public static class payType {
        public static String goods = "ShopPayActivity";
        public static String virtual = "ConfirmOrderActivity";
    }

    public static String getUid() {
        return (String) SPUtils.get(MyApplication.getInstance(), "uid", "");
    }

    public static void removeUserInfo() {
        if (SPUtils.contains(MyApplication.getInstance(), "uid")) {
            SPUtils.remove(MyApplication.getInstance(), "uid");
        }
        if (SPUtils.contains(MyApplication.getInstance(), "userInfo")) {
            SPUtils.remove(MyApplication.getInstance(), "userInfo");
        }
    }

    public static String getUserInfo() {
        return (String) SPUtils.get(MyApplication.getInstance(), "userInfo", "");
    }

    public static void saveData() {
        isLogin = true;
        if (userInfo != null) {
            saveUserId(userInfo.getUser_id());
            saveUserInfo(userInfo);
        } else {
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

    /**
     * 公共参数
     * 拼接get请求参数  签名
     */
    public static String joinStrParams(Map map) {
        map.put("platform", "app");
        map.put("tm", String.valueOf(new Date().getTime() / 1000));
        //TreeMap 默认按key排序
        TreeMap treemap = new TreeMap(map);
        String strA = MapUtils.Map2String(treemap);
        String signTemp = strA + "&key=LgcD6nx7mRCPweZT3C9fhESdRVkdIzIplxw8Q";
        Log.d("signTemp-->>", signTemp);
        String sign = MD5Utils.digest(signTemp).toUpperCase(); //生成签名
        Log.d("sign-->>", sign);
        return sign;
    }

    // key
//    public static final String shanyanKey = "BCtJkQBB";
    //      appid
    public static final String shanyanAppId = "vtGd8jr1";

    public static class getClassifyKey {
        public static final String DJM = "djm"; //大吉名
        public static final String XJM = "xjm"; //小吉名
        public static final String BZJP = "bzjp";//八字精批
        public static final String HYCS = "hycs";//婚姻测算
        public static final String CYFX = "cyfx";//财运分析
        public static final String BZHH = "bzhh";//八字合婚
        public static final String ZWDS = "zwds";//紫微斗数
        public static final String WLYS = "wlys";//未来运势
        public static final String XMXP = "xmxp";//姓名详批
        public static final String YLYY = "ylyy";//月老姻缘
        public static final String CSFX = "csfx";//测试分类
    }

    public static class getApi2 {
        public static final String URL = "http://dsj.zhouyi999.cn/";

        //大师列表
        public static final String DASHILIST = URL + "api/v1/dashi/list";

        //擅长技能列表
        public static final String SKILLLIST = URL + "api/v1/dashi/skill";

        //社区首页顶部分类
        public static final String VIPCATEGORY = URL + "api/v1/article/category";

        //社区首页
        public static final String VIPHOME = URL + "api/v1/community/index";

        //创建咨询订单
        public static final String CREATEZIXUNORDER = URL + "api/v1/zixun/create";

        //商品订单
        public static final String SHOPCREATEORDER = URL + "api/v1/order/create";

        //wx支付
        public static final String WXPAY = URL + "api/v1/order/wxpay";

        //文章详情
        public static final String ARTICLEDETAIL = URL + "api/v1/article/detail";

        //用户测算信息
        public static final String USERCESUANINFO = URL + "api/v1/user/ce_info";

        //文章列表
        public static final String ARTICLELIST = URL + "api/v1/article/list";
    }

    public static class getApi {
        public static final String URL = "http://app.zhouyi999.cn/";
//        public static final String URL = "http://192.168.2.248/"; //临时域名

        // 起名接口
        public static final String QIMING = URL + "index/names/addQiMing";

        // 八字精批接口
        public static final String BAZI = URL + "index/bazi/bzjp";

        // 获取验证码
        public static final String GETCODE = URL + "index/login/sendPhoneCode";

        // 获取绑定手机号验证码
        public static final String GETBINDCODE = URL + "index/user/WxBangGetCode";

        // 绑定手机号
        public static final String BINDPHONE = URL + "index/user/WxBangPhone";

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

        //         解梦大列表
        public static final String JIEMENGLIST = URL + "index/jmeng/getMengFl";
        //解梦小列表
        public static final String JIEMENGLISTSMALL = URL + "index/jmeng/mengFlList";
        //解梦具体内容
        public static final String JIEMENGDETAIL = URL + "index/jmeng/mengInfo";

        //用户分享次数
        public static final String ADDUSERSHARE = URL + "index/user/addUserShare";

        //用户分享url
        public static final String GETUSERSHAREURL = URL + "index/share/userShareUrl";

        //获取农历日期
        public static final String GETNONGLIDATE = URL + "index/index/index";

        //获取大师信息 大师微信客服
        public static final String GETKEFUINFO = URL + "index/user/getKefuinfo";

        //获取大师信息 大师微信客服
        public static final String GETSCROLLTEXT = URL + "index/order_comment/getyyCommentList";

        public static final String GETPRIVACYTEXT = URL + "index/privacy/privacyInfo";

    }
}