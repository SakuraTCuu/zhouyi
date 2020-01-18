package com.qicheng.zhouyi.ui.qiming.fragment;

import android.widget.TextView;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseFragment;
import com.qicheng.zhouyi.bean.QimingUserInfoBean;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

public class QimingBaziFragment extends BaseFragment {


    @BindView(R.id.tv_gender)
    TextView tv_gender;

    @BindView(R.id.tv_surname)
    TextView tv_surname;

    @BindView(R.id.tv_zodic)
    TextView tv_zodic;

    @BindView(R.id.tv_glday)
    TextView tv_glday;

    @BindView(R.id.tv_nlday)
    TextView tv_nlday;


    private JSONObject userInfo;
    private String nongliStr;

    public QimingBaziFragment() {
        super();
    }

    public QimingBaziFragment(JSONObject userInfo, String nongliStr) {
        this.userInfo = userInfo;
        this.nongliStr = nongliStr;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_qiming_bazi;
    }

    @Override
    protected void initView() {

        try {
            String glDay = userInfo.getInt("birthdayy") + "年" + userInfo.getInt("birthdaym") + "月" + userInfo.getInt("birthdayd") + "日 " + userInfo.getInt("birthdayh") + "时";
//            String glDay = userInfo.getBirthdayy() + "年" + userInfo.getBirthdaym() + "月" + userInfo.getBirthdayd() + "日 " + userInfo.getBirthdayh() + "时";
            String gender = userInfo.getString("gender");
            String name = userInfo.getString("name");
            tv_gender.setText(gender);
            tv_surname.setText(name);
            tv_zodic.setText(name);
            tv_glday.setText(glDay);
            tv_nlday.setText(nongliStr);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initData() {

    }
}
