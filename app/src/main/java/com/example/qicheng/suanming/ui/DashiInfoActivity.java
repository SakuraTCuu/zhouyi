package com.example.qicheng.suanming.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.adapter.DashiDetailBuyItemAdapter;
import com.example.qicheng.suanming.adapter.DashiDetailCommentAdapter;
import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.bean.DashiDetailBean;
import com.example.qicheng.suanming.bean.DashiDetailCommentBean;
import com.example.qicheng.suanming.common.ActivityManager;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.contract.DashiInfoContract;
import com.example.qicheng.suanming.presenter.DashiInfoPresenter;
import com.example.qicheng.suanming.utils.CustomDialog;
import com.example.qicheng.suanming.utils.ToastUtils;
import com.example.qicheng.suanming.widget.CustomListView;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bumptech.glide.util.Preconditions.checkNotNull;
//import static com.google.common.base.Preconditions.checkNotNull;

public class DashiInfoActivity extends BaseActivity implements DashiInfoContract.View, AdapterView.OnItemClickListener {

    @BindView(R.id.iv_dashi_icon)
    ImageView iv_dashi_icon;

    @BindView(R.id.tv_detail_orderNum)
    TextView tv_detail_orderNum;
    @BindView(R.id.tv_detail_attention)
    TextView tv_detail_attention;
    @BindView(R.id.tv_detail_replyTime)
    TextView tv_detail_replyTime;
    @BindView(R.id.tv_ruzhuTime)
    TextView tv_ruzhuTime;
    @BindView(R.id.tv_startTime)
    TextView tv_startTime;
    @BindView(R.id.tv_level)
    TextView tv_level;
    @BindView(R.id.tv_content)
    TextView tv_content;
//    @BindView(R.id.tv_dashi_content)
//    TextView tv_dashi_content;

    @BindView(R.id.tv_dashi_comment_1)
    TextView tv_dashi_comment_1;
    @BindView(R.id.tv_dashi_comment_2)
    TextView tv_dashi_comment_2;
    @BindView(R.id.tv_dashi_comment_3)
    TextView tv_dashi_comment_3;

    @BindView(R.id.tv_buy)
    TextView tv_buy;

    @BindView(R.id.tv_detail_name)
    TextView tv_detail_name;

    @BindView(R.id.ll_collect)
    LinearLayout ll_collect;

    @BindView(R.id.iv_collect)
    ImageView iv_collect;

    @BindView(R.id.lv_detail_comment_list)
    CustomListView lv_detail_comment_list;

    @BindView(R.id.ll_label_list)
    LinearLayout ll_label_list;

    @BindView(R.id.tv_collect)
    TextView tv_collect;

    private DashiInfoContract.Presenter mPresenter;

    private DashiDetailBuyItemAdapter buyItemAdapter;

    private DashiDetailCommentAdapter commentAdapter;
    private List<DashiDetailBean.DataBean.ProjectBean> project;
    private DashiDetailBean.DataBean.DashiBean dashiInfo;

    private String ds_id;

    private int share_type;
    private IWXAPI api;

    private int selectId = -1;

    private boolean isAttention = false;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_dash_info;
    }

    @Override
    protected void initView() {
        ActivityManager.getInstance().push(this);
        hideTitleBar();
//        setStatusBarColor(ContextCompat.getColor(this, R.color.dashi_info_statusbar));

        mPresenter = new DashiInfoPresenter(this);
//        mPresenter.start();

        ds_id = getIntent().getStringExtra("id");

        Map<String, String> map = new HashMap();
        map.put("ds_id", ds_id);
        mPresenter.getDashiInfo(map);

        Map<String, String> commonMap = new HashMap();
        commonMap.put("type", "1");
        commonMap.put("releate_id", ds_id);
        commonMap.put("per_page", "3");
        mPresenter.getCommentInfo(commonMap);

        showLoading();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.tv_buy, R.id.ll_collect, R.id.ll_detail_more, R.id.iv_exit, R.id.iv_share})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_collect: //点击收藏
                isAttention = !isAttention;
                updateAttentionState();
                clickAttention();
                break;
            case R.id.tv_buy: //点击下单
                //判断条件是否满足
                clickBuy();
                break;

            case R.id.iv_share: //点击分享
                clickShare();
                break;
            case R.id.ll_detail_more: //点击更多
                //跳转更多评论界面
                Intent intent = new Intent(DashiInfoActivity.this, MoreEvaluateActivity.class);
                intent.putExtra("id", ds_id);
                startActivity(intent);

                break;
            case R.id.iv_exit: //点击退出
                finish();
                break;
        }
    }

    public void clickShare() {
        //微信分享
        CustomDialog dialog = new CustomDialog(this) {
            @Override
            public void clickWx() {
                share_type = 1;
                wxShare(share_type);
            }

            @Override
            public void clickPyq() {
                share_type = 2;
                wxShare(share_type);
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
        webpageObject.webpageUrl = "https://baidu.com";

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
        msg.title = "大师集,高手如云";
        msg.description = "...";
        // 如果没有位图，可以传null，会显示默认的图片
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
//        msg.setThumbImage(bitmap);
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
    }

    public void clickBuy() {
//        if (selectId == -1) {
//            ToastUtils.showShortToast("请选择要测算的项目");
//            return;
//        }

        Intent intent = new Intent(DashiInfoActivity.this, SelectServerActivity.class);
        intent.putExtra("ds_id", ds_id);
//        intent.putExtra("avator", dashiInfo.getAvator());
        Bundle bundle = new Bundle();
        bundle.putSerializable("project", (Serializable) project);
        intent.putExtras(bundle);
        startActivity(intent);

//        Intent newIntent = new Intent(DashiInfoActivity.this, ConfirmOrderActivity.class);
//
//        newIntent.putExtra("ds_id", ds_id);
//        newIntent.putExtra("avator", dashiInfo.getAvator());

//        newIntent.putExtra("selectId", project.get(selectId).getId() + "");
//        newIntent.putExtra("name", project.get(selectId).getName());
//        newIntent.putExtra("price", project.get(selectId).getPrice());
//        startActivity(newIntent);
    }

    public void clickAttention() {
        Map<String, String> map = new HashMap();
        map.put("ds_id", ds_id);
        map.put("uid", Constants.getUid());
        mPresenter.setAttention(map);
    }

    public void updateAttentionState() {
        if (isAttention) {//TODO  换图
            iv_collect.setImageResource(R.mipmap.wujiaoxingliang);
            tv_collect.setText("已关注");
        } else {
            iv_collect.setImageResource(R.mipmap.wujiaoxingbuliang);
            tv_collect.setText("关注");
        }
    }

    @Override
    public void buySuc(String data) {

    }

    @Override
    public void getDashiInfoSuc(String data) {
        hideLoading();
        Log.d("dashiDetail-->>", data);

        DashiDetailBean bean = new Gson().fromJson(data, DashiDetailBean.class);
        dashiInfo = bean.getData().getDashi();
        project = bean.getData().getProject();

        Glide.with(this).load(dashiInfo.getAvator()).into(this.iv_dashi_icon);
//        Glide.with(this).load(dashiInfo.getBanner()).into(this.iv_dashi_banner);

        this.tv_detail_name.setText(dashiInfo.getName());
        this.tv_content.setText(Html.fromHtml(dashiInfo.getYelp()));
//        this.tv_dashi_content.setText(Html.fromHtml(dashiInfo.getIntroduce()));
        this.tv_level.setText(dashiInfo.getScore());
        this.tv_detail_orderNum.setText(dashiInfo.getOrder_num() + "");
        this.tv_detail_attention.setText(dashiInfo.getConcern() + "");
        this.tv_detail_replyTime.setText(dashiInfo.getReply_time() + "分钟");
        this.tv_ruzhuTime.setText(dashiInfo.getWorking_seniority() + "年");
        this.tv_startTime.setText("(" + dashiInfo.getJoin_time() + ")");
//        dashiInfo.getJoin_time()

        String[] labelList = dashiInfo.getLabel().split(",");

        isAttention = dashiInfo.getIs_concern() == 1 ? true : false;
        //更新 关注
        updateAttentionState();
        //label 设置
        setLabelList(ll_label_list, labelList);

//        buyItemAdapter = new DashiDetailBuyItemAdapter(mContext, project);
//        lv_detail_buy_list.setAdapter(buyItemAdapter);
//        lv_detail_buy_list.setOnItemClickListener(this);
    }

    private void setLabelList(LinearLayout ll, String[] labelList) {
        for (int i = 0; i < ll.getChildCount(); i++) {
            if (i < labelList.length - 1) {
                ((TextView) ll.getChildAt(i)).setText(labelList[i]);
            }
        }
    }

    @Override
    public void getCommentInfoSuc(String data) {
        hideLoading();

        Log.d("dashiDetail-->>", data);
        DashiDetailCommentBean bean = new Gson().fromJson(data, DashiDetailCommentBean.class);
        List<DashiDetailCommentBean.DataBean.ListBean> dashiInfo = bean.getData().getList();
        commentAdapter = new DashiDetailCommentAdapter(mContext, dashiInfo);
        lv_detail_comment_list.setAdapter(commentAdapter);
        lv_detail_comment_list.setOnItemClickListener(this);
    }

    @Override
    public void setPresenter(@NonNull DashiInfoContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (parent.getId() == R.id.lv_detail_buy_list) { //下单的
//            //更新 view
//            selectId = position;
//            buyItemAdapter.setSelectImg(position);
//            buyItemAdapter.notifyDataSetChanged();
//        } else if (parent.getId() == R.id.lv_detail_comment_list) { //评论
//
//        }
    }
}
