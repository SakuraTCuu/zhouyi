package com.qicheng.zhouyi.ui.fragment.mine;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseFragment;
import com.qicheng.zhouyi.bean.UserModel;
import com.qicheng.zhouyi.common.Constants;
import com.qicheng.zhouyi.ui.LoginActivity;
import com.qicheng.zhouyi.ui.MyApplication;
import com.qicheng.zhouyi.ui.mine.MineAboutMeActivity;
import com.qicheng.zhouyi.ui.mine.MineBeiyongActivity;
import com.qicheng.zhouyi.ui.mine.MineFeedbackActivity;
import com.qicheng.zhouyi.ui.mine.MineKeFuActivity;
import com.qicheng.zhouyi.ui.mine.MineOrderActivity;
import com.qicheng.zhouyi.ui.mine.MineUserActivity;
import com.qicheng.zhouyi.utils.ToastUtils;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelmsg.GetMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;

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

    @BindView(R.id.share_view)
    LinearLayout share_view;

    @BindView(R.id.ll_share)
    LinearLayout ll_share;

    @BindView(R.id.tv_nickname)
    TextView tv_nickname;

    private IWXAPI api;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @OnClick({R.id.about_view, R.id.feedback_view, R.id.user_view, R.id.kefu_view, R.id.order_view, R.id.logout_btn, R.id.user_beiyong})
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

        ll_share.setVisibility(View.VISIBLE);
//        //从底部弹出
//        api = MyApplication.getInstance().getWxApi();
//
//        if (!api.isWXAppInstalled()) {
//            ToastUtils.showShortToast("您还没有安装微信");
//            return;
//        }
//
//        if (api.getWXAppSupportAPI() >= Build.TIMELINE_SUPPORTED_SDK_INT) {
//            //do share
//            //分享到朋友圈
//        }
//
//        // 初始化一个WXWebpageObject对象
//        WXWebpageObject webpageObject = new WXWebpageObject();
//        // 填写网页的url
//        webpageObject.webpageUrl = "";
//
//        // 用WXWebpageObject对象初始化一个WXMediaMessage对象
//        WXMediaMessage msg = new WXMediaMessage(webpageObject);
//        // 填写网页标题、描述、位图
//        msg.title = "";
//        msg.description = "";
//        // 如果没有位图，可以传null，会显示默认的图片
//        msg.setThumbImage(null);
//
//        // 构造一个Req
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        // transaction用于唯一标识一个请求（可自定义）
//        req.transaction = "webpage";
//        // 上文的WXMediaMessage对象
//        req.message = msg;
//        // SendMessageToWX.Req.WXSceneSession是分享到好友会话
//        // SendMessageToWX.Req.WXSceneTimeline是分享到朋友圈
//        req.scene = SendMessageToWX.Req.WXSceneSession;
////        req.scene = SendMessageToWX.Req.WXSceneTimeline;
//        // 向微信发送请求
//        api.sendReq(req);
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
//        else if (Constants.getUserInfo() != null) {
//            userInfo =  Constants.getUserInfo();
//        }


    }

    @Override
    protected void initData() {

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