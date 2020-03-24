package com.qicheng.zhouyi.ui.fragment.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;

import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseFragment;
import com.qicheng.zhouyi.bean.JieMengBean;
import com.qicheng.zhouyi.bean.UserModel;
import com.qicheng.zhouyi.common.Constants;
import com.qicheng.zhouyi.common.OkHttpManager;
import com.qicheng.zhouyi.ui.LoginActivity;
import com.qicheng.zhouyi.ui.MainActivity;
import com.qicheng.zhouyi.ui.MyApplication;
import com.qicheng.zhouyi.ui.mine.MineAboutMeActivity;
import com.qicheng.zhouyi.ui.mine.MineBeiyongActivity;
import com.qicheng.zhouyi.ui.mine.MineFeedbackActivity;
import com.qicheng.zhouyi.ui.mine.MineKeFuActivity;
import com.qicheng.zhouyi.ui.mine.MineOrderActivity;
import com.qicheng.zhouyi.ui.mine.MineUserActivity;
import com.qicheng.zhouyi.utils.CustomDialog;
import com.qicheng.zhouyi.utils.ToastUtils;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelmsg.GetMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {

    @BindView(R.id.user_view)
    LinearLayout userView;

    @BindView(R.id.order_view)
    LinearLayout orderView;

    @BindView(R.id.feedback_view)
    LinearLayout feedbackView;

    @BindView(R.id.kefu_view)
    LinearLayout kefuView;

    @BindView(R.id.about_view)
    LinearLayout aboutView;

    @BindView(R.id.iv_touxiang)
    ImageView iv_touxiang;

    @BindView(R.id.tv_nickname)
    TextView tv_nickname;

    @BindView(R.id.rl_parent)
    RelativeLayout rl_parent;

    @BindView(R.id.ll_mine_root)
    LinearLayout ll_mine_root;

    private IWXAPI api;
    private int type = 1;
    private MainActivity.hideBottom listener;
    private String url = "";

    private String[] contentList = {"人生总有不如意，快来“星逸堂”免费算一卦吧！\n",
            "解读2020年财富，事业，健康，感情，机缘运势，寻找适合你的发展契机。",
            "一个人太累，想马上脱单。月老姻缘揭秘你的桃花正缘。",
            "什么是你财富自由上的最大阻碍？你的“八字”已经告诉你了。"};

    public MineFragment(MainActivity.hideBottom hideBottom) {
        this.listener = hideBottom;
    }

    @Override
    protected void initView() {
        //获取用户信息
        if (Constants.userInfo != null) {
            UserModel userInfo = Constants.userInfo;

            String nickName = userInfo.getNick_name();
            tv_nickname.setText(nickName);
            //显示图片
        } else {
            //错误!!!
            ToastUtils.showShortToast("用户信息获取失败!");
        }

        getShareUrl();
    }

    private void getShareUrl() {
        Map map = new HashMap();
        OkHttpManager.request(Constants.getApi.GETUSERSHAREURL, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    JSONObject jData = jsonObject.getJSONObject("data");
                    url = jData.getString("web_url");
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

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.about_view, R.id.feedback_view, R.id.user_view, R.id.kefu_view, R.id.order_view, R.id.share_view,
            R.id.logout_btn, R.id.user_beiyong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_view:
                gotoMineUserAcitivty();
                break;
            case R.id.user_beiyong:
                gotoMineBeiyongAcitivty();
                break;
            case R.id.order_view:
                gotoMineOrderAcitivty();
                break;
            case R.id.feedback_view:
                gotoFeedbackAcitivty();
                break;
            case R.id.kefu_view:
                gotoKeFuAcitivty();
                break;
            case R.id.about_view:
                gotoAboutMeAcitivty();
                break;
            case R.id.share_view:
                gotoWxShare();
                break;
            case R.id.logout_btn:
                //退出
                logoutBtn();
                break;
        }
    }

    private void gotoWxShare() {
        CustomDialog dialog = new CustomDialog(getActivity()) {
            @Override
            public void clickWx() {
                type = 1;
                wxShare(type);
            }

            @Override
            public void clickPyq() {
                type = 2;
                wxShare(type);
            }
        };
        dialog.show();
        api = MyApplication.getInstance().getWxApi();

        if (!api.isWXAppInstalled()) {
            ToastUtils.showShortToast("您还没有安装微信");
            return;
        }
    }

    private void wxShare(int type) {
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
        if (db > 0.25) {
            i = 0;
        } else if (db > 0.25 && db < 0.5) {
            i = 1;
        } else if (db > 0.25 && db < 0.5) {
            i = 2;
        } else {
            i = 3;
        }
        msg.title = "周易八卦,测算前世今生";
        msg.description = contentList[i];
        // 如果没有位图，可以传null，会显示默认的图片

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
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
        this.setData2Server();

    }

    private void setData2Server() {
        Map map = new HashMap();
        OkHttpManager.request(Constants.getApi.ADDUSERSHARE, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    boolean code = jsonObject.getBoolean("code");
                    String msg = jsonObject.getString("msg");
                    if (code) {
                        ToastUtils.showShortToast(msg);
                    } else {
                        ToastUtils.showShortToast(msg);
                    }
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

    public void logoutBtn() {
        //清除存储的数据
        //跳转到登录界面
        Constants.removeUserInfo();
        Constants.isLogin = false;
        startActivity(new Intent(mContext, LoginActivity.class));
        getActivity().finish();
    }

    public void gotoKeFuAcitivty() {
        startActivity(new Intent(mContext, MineKeFuActivity.class));
    }

    public void gotoFeedbackAcitivty() {
        startActivity(new Intent(mContext, MineFeedbackActivity.class));
    }

    public void gotoAboutMeAcitivty() {
        startActivity(new Intent(mContext, MineAboutMeActivity.class));
    }

    public void gotoMineOrderAcitivty() {
        startActivity(new Intent(mContext, MineOrderActivity.class));
    }

    public void gotoMineUserAcitivty() {
        startActivity(new Intent(mContext, MineUserActivity.class));
    }

    public void gotoMineBeiyongAcitivty() {
        startActivity(new Intent(mContext, MineBeiyongActivity.class));
    }

}