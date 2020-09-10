package com.example.qicheng.suanming.ui;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codbking.widget.OnChangeLisener;
import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.adapter.DashiDetailBuyItemAdapter;
import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.bean.DashiDetailBean;
import com.example.qicheng.suanming.bean.UserCeSuanInfo;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.contract.SelectServerContract;
import com.example.qicheng.suanming.presenter.SelectServerPresenter;
import com.example.qicheng.suanming.utils.CustomDateDialog;
import com.example.qicheng.suanming.utils.DataCheck;
import com.example.qicheng.suanming.utils.GlnlUtils;
import com.example.qicheng.suanming.utils.ToastUtils;
import com.example.qicheng.suanming.widget.CustomListView;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectServerActivity extends BaseActivity implements AdapterView.OnItemClickListener, SelectServerContract.View {

    @BindView(R.id.et_user_name)
    EditText et_user_name;

    @BindView(R.id.et_user_phone)
    EditText et_user_phone;

    @BindView(R.id.iv_gender_man)
    ImageView iv_gender_man;

    @BindView(R.id.iv_gender_woman)
    ImageView iv_gender_woman;

    @BindView(R.id.lv_detail_buy_list)
    CustomListView lv_detail_buy_list;

    @BindView(R.id.tv_pay_all)
    TextView tv_pay_all;

    @BindView(R.id.tv_user_birthday)
    TextView tv_user_birthday;

    private DashiDetailBuyItemAdapter buyItemAdapter;
    private int selectId = -1;

    private String ds_id;

    private List<DashiDetailBean.DataBean.ProjectBean> project;

    private int gender = 1;//1 男  2 女
    private boolean isGL = true;
    private GlnlUtils.glnlType nlType;
    private String birthday;
    private Calendar cDate;

    private SelectServerPresenter mPresenter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_select_server;
    }

    @Override
    protected void initView() {
        setTitleText("确认订单");

        mPresenter = new SelectServerPresenter(this);

        ds_id = getIntent().getStringExtra("ds_id");
        project = (List<DashiDetailBean.DataBean.ProjectBean>) getIntent().getSerializableExtra("project");

        buyItemAdapter = new DashiDetailBuyItemAdapter(mContext, project);
        lv_detail_buy_list.setAdapter(buyItemAdapter);
        lv_detail_buy_list.setOnItemClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.tv_buy, R.id.iv_gender_man, R.id.iv_gender_woman, R.id.tv_user_birthday})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.iv_gender_woman:
                gender = 2;
                iv_gender_woman.setImageResource(R.mipmap.shoppingcart_duigou);
                iv_gender_man.setImageResource(R.mipmap.shopping_quan);
                break;
            case R.id.iv_gender_man:
                gender = 1;
                iv_gender_woman.setImageResource(R.mipmap.shopping_quan);
                iv_gender_man.setImageResource(R.mipmap.shoppingcart_duigou);
                break;
            case R.id.tv_buy:
                clickBuy();
                break;
            case R.id.tv_user_birthday:
                clickBirthday();
                break;
        }
    }

    public void clickBirthday() {
        showDatePicker();
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

    public void clickBuy() {
        if (selectId == -1) {
            ToastUtils.showShortToast("请选择测算项目");
            return;
        }
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
        map.put("uid", Constants.getUid());

        mPresenter.putUserInfo(map);

        showLoading();
    }

    public void updateText() {
        String price = project.get(selectId).getPrice();
        tv_pay_all.setText("¥" + price);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectId = position;
        buyItemAdapter.setSelectImg(position);
        buyItemAdapter.notifyDataSetChanged();
        updateText();
    }

    @Override
    public void putUserInfoSuc(String data) {
        hideLoading();
        UserCeSuanInfo bean = new Gson().fromJson(data, UserCeSuanInfo.class);
//        {"code":200,"msg":"保存成功","data":{"info_id":9}}

        if (bean.getCode()) {
            int info_id = bean.getData().getInfo_id();
            gotoPay(info_id);
        } else {
            ToastUtils.showShortToast("错误," + bean.getMsg());
        }
    }

    public void gotoPay(int info_id) {
        Intent intent = new Intent(SelectServerActivity.this, DashiZixunPayActivity.class);
        intent.putExtra("type", "zixun");
        intent.putExtra("selectId", project.get(selectId).getId() + "");
        intent.putExtra("ds_id", ds_id);
        intent.putExtra("info_id", info_id + "");
        intent.putExtra("price", project.get(selectId).getPrice());
        startActivity(intent);
    }

    @Override
    public void setPresenter(SelectServerContract.Presenter presenter) {

    }
}
