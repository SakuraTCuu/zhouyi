package com.example.qicheng.suanming.ui.qiming.fragment;

import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.base.BaseFragment;

import org.json.JSONArray;
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

    @BindView(R.id.tv_bazi_shen_0)
    TextView tv_bazi_shen_0;
    @BindView(R.id.tv_bazi_shen_1)
    TextView tv_bazi_shen_1;
    @BindView(R.id.tv_bazi_shen_2)
    TextView tv_bazi_shen_2;
    @BindView(R.id.tv_bazi_shen_3)
    TextView tv_bazi_shen_3;

    @BindView(R.id.tv_bazi_qian_0)
    TextView tv_bazi_qian_0;
    @BindView(R.id.tv_bazi_qian_1)
    TextView tv_bazi_qian_1;
    @BindView(R.id.tv_bazi_qian_2)
    TextView tv_bazi_qian_2;
    @BindView(R.id.tv_bazi_qian_3)
    TextView tv_bazi_qian_3;

    @BindView(R.id.tv_bazi_cang_0)
    TextView tv_bazi_cang_0;
    @BindView(R.id.tv_bazi_cang_1)
    TextView tv_bazi_cang_1;
    @BindView(R.id.tv_bazi_cang_2)
    TextView tv_bazi_cang_2;
    @BindView(R.id.tv_bazi_cang_3)
    TextView tv_bazi_cang_3;

    @BindView(R.id.tv_bazi_taixi)
    TextView tv_bazi_taixi;
    @BindView(R.id.tv_bazi_taiyuan)
    TextView tv_bazi_taiyuan;
    @BindView(R.id.tv_bazi_minggong)
    TextView tv_bazi_minggong;


    @BindView(R.id.tv_bazi_na_0)
    TextView tv_bazi_na_0;
    @BindView(R.id.tv_bazi_na_1)
    TextView tv_bazi_na_1;
    @BindView(R.id.tv_bazi_na_2)
    TextView tv_bazi_na_2;
    @BindView(R.id.tv_bazi_na_3)
    TextView tv_bazi_na_3;


    @BindView(R.id.iv_src_jin)
    ImageView iv_src_jin;
    @BindView(R.id.iv_src_mu)
    ImageView iv_src_mu;
    @BindView(R.id.iv_src_shui)
    ImageView iv_src_shui;
    @BindView(R.id.iv_src_huo)
    ImageView iv_src_huo;
    @BindView(R.id.iv_src_tu)
    ImageView iv_src_tu;


    private JSONObject userInfo;
    private String nongliStr;
    private JSONArray qr;
    private JSONObject jreturn;
    private JSONObject pp;
    private String zodic;

    public QimingBaziFragment() {
        super();
    }

    public QimingBaziFragment(JSONObject userInfo, String nongliStr, JSONArray qr, JSONObject jreturn, JSONObject pp, String zodic) {
        this.userInfo = userInfo;
        this.nongliStr = nongliStr;
        this.qr = qr;
        this.jreturn = jreturn;
        this.pp = pp;
        this.zodic = zodic;
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
            tv_zodic.setText(zodic);
            tv_glday.setText(glDay);
            tv_nlday.setText(nongliStr);

            String shishen1 = pp.getString("shishen1");
            String shishen2 = pp.getString("shishen2");
            String shishen3 = pp.getString("shishen3");
            String shishen4 = pp.getString("shishen4");

            String zanggan1 = pp.getString("zanggan1");
            String zanggan2 = pp.getString("zanggan2");
            String zanggan3 = pp.getString("zanggan3");
            String zanggan4 = pp.getString("zanggan4");

            String nayin1 = pp.getString("nayin1");
            String nayin2 = pp.getString("nayin2");
            String nayin3 = pp.getString("nayin3");
            String nayin4 = pp.getString("nayin4");


            String minggong = pp.getString("minggong");
            String taiyuan = pp.getString("taiyuan");

            JSONArray jar = jreturn.getJSONObject("user").getJSONArray("bazi");
            jar.getString(0);
            String taixi = jar.getString(6) + jar.getString(7);

            //展示八字信息
            tv_bazi_shen_0.setText(Html.fromHtml(shishen3));
            tv_bazi_shen_1.setText(Html.fromHtml(shishen1));
            tv_bazi_shen_2.setText(Html.fromHtml(shishen4));
            tv_bazi_shen_3.setText(Html.fromHtml(shishen2));

            tv_bazi_qian_0.setText(Html.fromHtml(shishen1));
            tv_bazi_qian_1.setText(Html.fromHtml(shishen2));
            tv_bazi_qian_2.setText(Html.fromHtml(shishen3));
            tv_bazi_qian_3.setText(Html.fromHtml(shishen4));

            tv_bazi_cang_0.setText(zanggan1);
            tv_bazi_cang_1.setText(zanggan2);
            tv_bazi_cang_2.setText(zanggan3);
            tv_bazi_cang_3.setText(zanggan4);

            tv_bazi_taixi.setText(taixi);
            tv_bazi_taiyuan.setText(taiyuan);
            tv_bazi_minggong.setText(minggong);
//
//            tv_bazi_di_0.setText();
//            tv_bazi_di_1.setText();
//            tv_bazi_di_2.setText();
//            tv_bazi_di_3.setText();
//
            tv_bazi_na_0.setText(nayin1);
            tv_bazi_na_1.setText(nayin2);
            tv_bazi_na_2.setText(nayin3);
            tv_bazi_na_3.setText(nayin4);

            //设置喜用神分析
            Float jin = Float.parseFloat(qr.getJSONArray(0).getString(1));
            Float mu = Float.parseFloat(qr.getJSONArray(1).getString(1));
            Float shui = Float.parseFloat(qr.getJSONArray(2).getString(1));
            Float huo = Float.parseFloat(qr.getJSONArray(3).getString(1));
            Float tu = Float.parseFloat(qr.getJSONArray(4).getString(1));

            int img_jin = getResource(jin);
            int img_mu = getResource(mu);
            int img_shui = getResource(shui);
            int img_huo = getResource(huo);
            int img_tu = getResource(tu);

            iv_src_jin.setImageResource(img_jin);
            iv_src_mu.setImageResource(img_mu);
            iv_src_shui.setImageResource(img_shui);
            iv_src_huo.setImageResource(img_huo);
            iv_src_tu.setImageResource(img_tu);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private int getResource(float fl) {
        if (fl <= 15) {
            return R.mipmap.circle_15;
        } else if (fl > 15 && fl <= 30) {
            return R.mipmap.circle_30;
        } else if (fl > 30 && fl <= 45) {
            return R.mipmap.circle_45;
        } else if (fl > 45 && fl <= 60) {
            return R.mipmap.circle_45;
        } else if (fl >= 90) {
            return R.mipmap.circle_100;
        }
        return R.mipmap.circle_45;
    }

    @Override
    protected void initData() {

    }
}
