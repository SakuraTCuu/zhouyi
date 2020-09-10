package com.example.qicheng.suanming.ui.Article;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codbking.widget.OnChangeLisener;
import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.bean.ArticleDetailBean;
import com.example.qicheng.suanming.bean.UserCeSuanInfo;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.contract.ArticleDetailContract;
import com.example.qicheng.suanming.presenter.ArticleDetailPresenter;
import com.example.qicheng.suanming.ui.DashiZixunPayActivity;
import com.example.qicheng.suanming.utils.CustomDateDialog;
import com.example.qicheng.suanming.utils.CustomPopup;
import com.example.qicheng.suanming.utils.DataCheck;
import com.example.qicheng.suanming.utils.GlnlUtils;
import com.example.qicheng.suanming.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ArticleDetailActivity extends BaseActivity implements ArticleDetailContract.View {

    @BindView(R.id.tv_article_detail_title)
    TextView tv_article_detail_title;

    @BindView(R.id.tv_detail_content)
    TextView tv_detail_content;

    @BindView(R.id.tv_detail_test)
    TextView tv_detail_test;

    @BindView(R.id.tv_detail_comment)
    TextView tv_detail_comment;

    @BindView(R.id.tv_detail_haoping)
    TextView tv_detail_haoping;

    @BindView(R.id.tv_detail_explain)
    TextView tv_detail_explain;

    @BindView(R.id.tv_detail_total_price)
    TextView tv_detail_total_price;

    @BindView(R.id.tv_detail_origin_price)
    TextView tv_detail_origin_price;

    @BindView(R.id.tv_detail_sub_price)
    TextView tv_detail_sub_price;

    @BindView(R.id.iv_detail_banner)
    ImageView iv_detail_banner;

    @BindView(R.id.tv_user_info_name)
    TextView tv_user_info_name;
    @BindView(R.id.tv_user_info)
    TextView tv_user_info;

    private ArticleDetailPresenter mPresenter;
    private ArticleDetailBean.DataBean result;

    private boolean isGL = true;
    private GlnlUtils.glnlType nlType;
    private String birthday;
    private Calendar cDate;

    private String articleId = "0";
    private String title = "";

    EditText et_user_name;
    EditText et_user_phone;
    TextView tv_user_birthday;
    LinearLayout ll_submit;
    ImageView iv_gender_man;
    ImageView iv_gender_woman;
    ImageView iv_close;

    private int info_id;

    private int gender = 1;//1 男  2 女
    private CustomPopup popupWindow;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected void initView() {
        title = getIntent().getStringExtra("name");
        articleId = getIntent().getStringExtra("id");
        setTitleText(title);
        mPresenter = new ArticleDetailPresenter(this);
    }

    @Override
    protected void initData() {
//        Map<String, String> map = new HashMap<>();
//        map.put("article_id", articleId);
//        mPresenter.getArticleDetail(map);

        Map<String, String> newMap = new HashMap<>();
        newMap.put("article_id", articleId);
        newMap.put("uid", Constants.getUid());
        mPresenter.getArticleUserInfo(newMap);
        showLoading();
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void getArticleUserInfoSuc(String data) {
        hideLoading();
        Log.d("getArticleDetailSuc-->>", data);
        ArticleDetailBean bean = new Gson().fromJson(data, ArticleDetailBean.class);
        result = bean.getData();
        tv_article_detail_title.setText(result.getTitle());
        tv_detail_content.setText(Html.fromHtml(result.getContent()));
        tv_detail_comment.setText(result.getRecomment_num() + "评论");
        tv_detail_haoping.setText(result.getGood_rate() + "%好评率");
        tv_detail_test.setText(result.getUse_num() + "测试");

        tv_detail_explain.setText(result.getName());

        Glide.with(this).load(result.getBanner()).into(iv_detail_banner);

        tv_detail_origin_price.setText(result.getPrice() + "元");
        tv_detail_total_price.setText(result.getPrice() + "元");
        tv_detail_sub_price.setText(result.getCoupon_amount() + "元");

        String userStr = result.getCe_info().getName() + "  ";
        userStr += (result.getCe_info().getSex() == 1 ? "男" : "女") + "  ";
        userStr += result.getCe_info().getBirthday() + "  ";
        userStr += result.getCe_info().getPhone() + "  ";
        tv_user_info_name.setText(result.getCe_info().getName());
        tv_user_info.setText(userStr);
    }

    @Override
    public void getArticleDetailSuc(String data) {

    }

    @Override
    public void addUserInfoSuc(String data) {
        hideLoading();
        UserCeSuanInfo bean = new Gson().fromJson(data, UserCeSuanInfo.class);
        if (bean.getCode()) {
            info_id = bean.getData().getInfo_id();
            //关闭窗口
            popupWindow.close();
            bgAlpha(1.0f);
        } else {
            ToastUtils.showShortToast("错误," + bean.getMsg());
        }
    }

//    @Override
//    public void getArticleUserInfoSuc(String data) {
//        Log.d("ArticleUserInfo-->>", data);
//        /**
//         * {"code":true,"msg":"success","data":{"id":32,"name":"我的命中注定的人在哪里呢","title":"精准测算","category_id":3,"index_pic":"20200606\/2020-06-06_1591430821_5edb4ea511923.png","thumb":"20200730\/2020-07-30_1596076080_5f2230308bc67.jpg","detail_pic":"https:\/\/dsj.zhouyi999.cn\/uploads\/20200606\/2020-06-06_1591430721_5edb4e413ed67.png","content":"<p>所谓“命中注定”，并不是从看到的第一眼起，就笃定下一秒你们就会结婚，一辈子生活在一起。这样的“命中注定”就太玄乎，太不切实际了。<\/p><p>“命中注定”就是两个人在未来可以长期共同生活、和谐相处，可以彼此理解和支持，享受一种轻松愉悦的相处氛围。<\/p><p>遇到这样一个命中注定的爱人，无疑是幸运和幸福的。如果你的身边出现了这样一个人，请一定要牢牢抓住。<\/p><p><br\/><\/p>","use_num":233013,"recomment_num":453,"good_rate":"99.0","status":1,"is_show":1,"label":"精准","price":"49.00","created_at":"2020-06-06 16:07:11","updated_at":"2020-07-30 10:28:01","banner":"https:\/\/dsj.zhouyi999.cn\/uploads\/20200619\/2020-06-19_1592552217_5eec6b1989f87.png","ce_info":{"id":8,"uid":375,"name":"王坤","sex":1,"birthday":"1987-9-10","phone":"13073704201","created_at":"2020-09-10 17:50:22"},"coupon_amount":0,"pay_amount":"49.00"}}
//         */
//    }

    public void gotoPay(int info_id) {
        Intent intent = new Intent(ArticleDetailActivity.this, DashiZixunPayActivity.class);
        intent.putExtra("type", "vip");
        intent.putExtra("selectId", result.getId() + "");
        intent.putExtra("info_id", info_id + "");
        intent.putExtra("price", result.getPrice());
        startActivity(intent);
    }

    @OnClick({R.id.btn_article_detail_pay, R.id.iv_user_add})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_article_detail_pay:
                clickBuy();
                break;
            case R.id.iv_user_add:
                clickAddUser();
                break;
        }
    }

    public void clickBuy() {
        gotoPay(info_id);
    }

    public void clickAddUser() {
        //加载弹出框的布局                                      layout_userinfo_popup
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_userinfo_popup, null);
        //弹出填写用户信息框
        popupWindow = new CustomPopup((ViewGroup) contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置这两个属性
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setEditDismiss(false);
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);

        et_user_name = contentView.findViewById(R.id.et_user_name);
        et_user_phone = contentView.findViewById(R.id.et_user_phone);
        tv_user_birthday = contentView.findViewById(R.id.tv_user_birthday);
        ll_submit = contentView.findViewById(R.id.ll_submit);
        iv_gender_man = contentView.findViewById(R.id.iv_gender_man);
        iv_gender_woman = contentView.findViewById(R.id.iv_gender_woman);
        iv_close = contentView.findViewById(R.id.iv_close);
        tv_user_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        iv_gender_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = 1;
                iv_gender_man.setImageResource(R.mipmap.shoppingcart_duigou);
                iv_gender_woman.setImageResource(R.mipmap.shopping_quan);
            }
        });

        iv_gender_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = 2;
                iv_gender_woman.setImageResource(R.mipmap.shoppingcart_duigou);
                iv_gender_man.setImageResource(R.mipmap.shopping_quan);
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.close();
                bgAlpha(1.0f);
            }
        });

        ll_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSubmit();
            }
        });


        bgAlpha(0.5f);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return false;
        }
        return super.dispatchTouchEvent(event);
    }

    private void bgAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    /**
     * 展示日期
     */
    public void showDatePicker() {
        CustomDateDialog dialog = new CustomDateDialog(mContext);
        //设置上下年分限制
        dialog.setYearLimt(50);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
//        dialog.setType(DateType.TYPE_YMDHM);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //设置选择回调
        dialog.setOnChangeLisener(new OnChangeLisener() {
            @Override
            public void onChanged(Date date) {
                //日期监听
            }
        });

        //设置点击确定按钮回调
        dialog.setOnSureLisener(new CustomDateDialog.OnCustomSureLisener() {
            public void onSure(Date date, boolean flag) {
                isGL = flag;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                cDate = calendar;
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;  //月份从0开始算起
                int day = calendar.get(Calendar.DATE);
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);

                //小于10 前边加0   如 9月 会变成09月
                String monthStr = addZero2Date(month);
                String dayStr = addZero2Date(day);
                String hourStr = addZero2Date(hour);
                String minuteStr = addZero2Date(minute);

                if (!flag) { //农历
                    String glDate = year + monthStr + dayStr;
                    String nlDate;
                    try {
                        nlType = GlnlUtils.lunarToSolar(glDate, false);
                        nlDate = nlType.getTypeString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        String dateStr = year + "年" + monthStr + "月" + dayStr;
                        nlType = new GlnlUtils.glnlType(year + "", monthStr, dayStr, dateStr);
                        nlDate = dateStr;
                    }
                    tv_user_birthday.setText(nlDate);
                } else {
                    String dateStr = year + "年" + monthStr + "月" + dayStr + "    " + hourStr + ":" + minuteStr;
                    tv_user_birthday.setText(dateStr);
                }
            }
        });
        dialog.show();
    }

    /**
     * add a zero to any number less than 10 and greater than 0;
     *
     * @param i 转换日期
     * @return
     */
    public String addZero2Date(int i) {
        String str;
        if (i < 10) {
            str = "0" + i;
        } else {
            str = "" + i;
        }
        return str;
    }


    public void clickSubmit() {
        String inputName = et_user_name.getText().toString().trim();
        if (!DataCheck.isHanzi(inputName)) {
            ToastUtils.showShortToast("请输入正确姓名");
            return;
        }
        String inputPhone = et_user_phone.getText().toString().trim();
        if (!DataCheck.isCellphone(inputPhone)) {
            ToastUtils.showShortToast("请输入正确手机号码");
            return;
        }

        //计算时间是否正确
        if (cDate == null) {
            ToastUtils.showShortToast("请选择时间");
            return;
        }
        String month = String.valueOf(cDate.get(Calendar.MONTH) + 1);

        if (isGL) {
            birthday = cDate.get(Calendar.YEAR) + "-" + month + "-" + cDate.get(Calendar.DATE);
        } else {
            birthday = nlType.getString();
        }

        Map<String, String> map = new HashMap();
        map.put("name", inputName);
        map.put("sex", gender + "");
        map.put("birthday", birthday);
        map.put("phone", inputPhone);

        mPresenter.addUserInfo(map);

        String userStr = inputName + "  ";
        userStr += (gender == 1 ? "男" : "女") + "  ";
        userStr += birthday + "  ";
        userStr += inputPhone + "  ";
        tv_user_info_name.setText(inputName);
        tv_user_info.setText(userStr);

        showLoading();
    }

    @Override
    public void setPresenter(ArticleDetailContract.Presenter presenter) {

    }
}
