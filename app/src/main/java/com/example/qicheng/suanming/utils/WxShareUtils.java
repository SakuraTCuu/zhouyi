package com.example.qicheng.suanming.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.ui.MyApplication;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sakura on 2020-04-22 09:07
 */
public class WxShareUtils {

    private static IWXAPI api;
    private static String url;

    private static String[] contentList = {"人生总有不如意，快来“星逸堂”免费算一卦吧！\n",
            "解读2020年财富，事业，健康，感情，机缘运势，寻找适合你的发展契机。",
            "一个人太累，想马上脱单。月老姻缘揭秘你的桃花正缘。",
            "什么是你财富自由上的最大阻碍？你的“八字”已经告诉你了。"};

    public static void showWxShare(String shareUrl, int type) {

        type = (type > 2 || type < 1) ? 1 : type;

        if (shareUrl == null || shareUrl.equals("")) {
            ToastUtils.showShortToast("非法url");
            return;
        }

        url = shareUrl;

        api = MyApplication.getInstance().getWxApi();

        if (!api.isWXAppInstalled()) {
            ToastUtils.showShortToast("您还没有安装微信");
            return;
        }

        wxShare(type);
    }

    public static void wxShare(int type) {
        if (api == null) {
            api = MyApplication.getInstance().getWxApi();
        }

        boolean isSupportPyq = api.getWXAppSupportAPI() >= Build.TIMELINE_SUPPORTED_SDK_INT;
        if (!isSupportPyq && type == 2) {
            ToastUtils.showShortToast("请安装新版微信分享");
            return;
        }
        // 初始化一个WXWebpageObject对象
        WXWebpageObject webpageObject = new WXWebpageObject();

        // 填写网页的url
        webpageObject.webpageUrl = url;

        // 用WXWebpageObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        // 填写网页标题、描述、位图
        double db = Math.random();
        int i;
        if (db < 0.25) {
            i = 0;
        } else if (db > 0.25 && db < 0.5) {
            i = 1;
        } else if (db > 0.5 && db < 0.75) {
            i = 2;
        } else {
            i = 3;
        }
        msg.title = "周易八卦,测算前世今生";
        msg.description = contentList[i];
        // 如果没有位图，可以传null，会显示默认的图片

        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.mipmap.logo);
        msg.setThumbImage(bitmap);
        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        // transaction用于唯一标识一个请求（可自定义）
        req.transaction = "webpage";
        // 上文的WXMediaMessage对象
        req.message = msg;
        if (type == 1) { //微信
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if (type == 2) { //朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        // 向微信发送请求
        api.sendReq(req);
        //调用成功的方法
        setData2Server();
    }

    private static void setData2Server() {
        Map map = new HashMap();
        OkHttpManager.request(Constants.getApi.ADDUSERSHARE, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    boolean code = jsonObject.getBoolean("code");
                    String msg = jsonObject.getString("msg");
//                    if (code) {
//                        ToastUtils.showShortToast("分享成功");
//                    } else {
//                        ToastUtils.showShortToast(msg);
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.d("info---->>", info.toString());
                String result = info.getRetDetail();
                ToastUtils.showShortToast(result);
            }
        });

    }

}
