package com.qicheng.zhouyi.ui.jiemeng;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.library.flowlayout.FlowAdapter;
import com.library.flowlayout.FlowLayout;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.bean.JieMengBean;
import com.qicheng.zhouyi.bean.JieMengListBean;
import com.qicheng.zhouyi.common.Constants;
import com.qicheng.zhouyi.common.OkHttpManager;
import com.qicheng.zhouyi.ui.bazijingpi.BaziJingPiActivity;
import com.qicheng.zhouyi.ui.webView.NamePayActivity;
import com.qicheng.zhouyi.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Sakura on 2020-03-21 10:57
 */
public class JiemengActivity extends BaseActivity {

    @BindView(R.id.et_input_jiemeng)
    EditText et_input_jiemeng;

    @BindView(R.id.flow)
    FlowLayout flow;

    private String input;
    private int[] tempidList;
    private final List<JieMengBean> itemList = new ArrayList<>();
    private int searchPage = 1;
    private int clickPage = 1;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_jiemeng;
    }

    @Override
    protected void initView() {
        //进入就请求
        this.getJieDataFromServer();
    }

    private void setData() {
        final MyFlowAdapter myFlowAdapter = new MyFlowAdapter(this, itemList);
        flow.setAdapter(myFlowAdapter);
    }

    class MyFlowAdapter extends FlowAdapter<JieMengBean> {

        public MyFlowAdapter(Context context, List<JieMengBean> list) {
            super(context, list);
        }

        @Override
        protected int generateLayout(int position) {
            return R.layout.flow_item;
        }

        @Override
        protected void getView(final JieMengBean o, View parent) {
            TextView text = (TextView) parent.findViewById(R.id.flow_text);
            text.setText(o.getTempName());
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDataFromServer(o.getTempid(), o.getTempName());
                }
            });
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
    }

    @OnClick({R.id.btn_jiemeng})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_jiemeng:
                this.getDataFromServer(-1,"");
                break;
        }
    }

    private void getJieDataFromServer() {
        //空
        Map<String, String> map = new HashMap<>();

//        {"code":true,"msg":"操作成功","data":[{"tempid":1,"tempname":"分类解梦"},{"tempid":2,"tempname":"人物类"},
//        {"tempid":3,"tempname":"情爱类"},{"tempid":4,"tempname":"生活类"},{"tempid":5,"tempname":"物品类"},
//        {"tempid":6,"tempname":"身体类"},{"tempid":7,"tempname":"动物类"},{"tempid":8,"tempname":"植物类"},
//        {"tempid":9,"tempname":"鬼神类"},{"tempid":10,"tempname":"建筑类"},{"tempid":11,"tempname":"自然类"}]}
        OkHttpManager.request(Constants.getApi.JIEMENGLIST, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    JSONArray jar = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jar.length(); i++) {
                        JSONObject jdata = jar.getJSONObject(i);
                        JieMengBean bean = new JieMengBean(jdata.getString("tempname"), jdata.getInt("tempid"));
                        itemList.add(bean);
                    }
                    setData();
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

    private void getDataFromServer(int type, String bigText) {

        Map<String, String> map = new HashMap<>();
        if (type == -1) {
            //点击事件和输入框
            input = et_input_jiemeng.getText().toString().trim();
            map.put("search_key", input);
            map.put("page", searchPage + "");
        } else {
            map.put("tempid", type + "");
            map.put("page", clickPage + "");
        }

        OkHttpManager.request(Constants.getApi.JIEMENGLISTSMALL, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    Log.d("jsonObject---->>", jsonObject.toString());
                    boolean code = jsonObject.getBoolean("code");
                    if (code) {
                        Intent intent = new Intent(mContext, JiemengListActivity.class);
                        intent.putExtra("data", jsonObject.toString());
                        intent.putExtra("bigText", bigText);
                        startActivity(intent);
                    } else {
                        String msg = jsonObject.getString("msg");
                        ToastUtils.showShortToast(msg);
                    }
//                    {"code":true,"msg":"操作成功","data":[{"id":3497,"keyboard":"心","title":"梦见伤心","smalltext":"document.write(\\'\\');\ntanx_s = document.createElement(\\\"script\\\");\ntanx_s.type = \\\"text\/javascript\\\";\ntanx_s.charset = \\\"gbk\\\";\ntanx_s.id = \\\"tanx-s-mm_10058411_4154013_13476736\\\";\ntanx_s.async = true;\ntanx_s.src = \\\"http:\/\/p.tanx.com\/ex?i=mm_10058411_415"},{"id":3499,"keyboard":"面条","title":"梦见面条","smalltext":"面条——面条主福，象征长长久久，而梦见面条或是自己在做面条、煮面条、吃面条，都表示健康长寿，事业进步，会有好运气。梦见面条——预示着你由于近期锻炼身体使得现在的身体很好、很健康，以后要继续保持现在的心态，并且坚持锻炼身体；而且近日你还有意外的惊喜，是吉兆。梦到吃面条——预示着身体健康，好事即来。梦到做面条——预示着会有好运降临。梦见吃阳春面——预示着你可能会遇到一见钟情的异性，要慎重交往，避免冲动。梦见吃通心面——预示着你身体各方面的抵抗力增强，你的健康运来了。病人梦见煮面条——预示着你的病情会好转，身体"},{"id":3501,"keyboard":"路","title":"梦见扫路","smalltext":"梦见扫路——预示着你内心蠢蠢欲动，有出轨的欲望；同时会有应酬上的开销，花钱不眨眼，与人沟通变得很频繁，你有很多公开露脸说话、开会、谈事情的机会。梦见别人扫路——预示着你在接下来的日子达到一个什么样的目标，中长期的也好，短期的也好，今天都可以用心去定下计划，统筹策划能力相当不错呢；学习目标变得功利，实用性强的科目会赢得你的关注，你有很多想法，并不是单靠掌握理论就能实现的。你要谨防自己的天真。梦见扫马路——预示着你觉得自己需要放纵一下，整个人都因此变得散漫随便起来，喝闷酒寻开心的情况会发生，甚至也会出现在性方"},{"id":3503,"keyboard":"银行","title":"梦见抢银行","smalltext":"  梦见银行——预示着你的事业会兴旺发达，是吉兆。  梦见抢银行——预示着你手头很缺钱，或是对钱的重视关注度极高，极其渴望的一种心态，而且偶尔还有非分之想，或者有想过用不正当的方式弄到钱，只是没能真正实施而已，对现实生活的不满。  梦见自己抢银行——预示着你很想谈恋爱，或是渴望有性爱，希望能安全享受美好的恋爱或性爱关系。  梦见抢银行成功——预示着你最近会有意外的钱财，是好的预兆。  求学者梦见抢银行——预示着你最近的学习成绩会很差。  梦见抢银行并杀人——预示着你的潜意识里很暴躁很焦躁，很想发泄，做事往"},{"id":3504,"keyboard":"自行车,车,坡","title":"梦见骑自行车下坡","smalltext":"梦见骑自行车下坡——预示着你对目前的生活有些控制不住的意思，暗示自己不能控制当前的工作和生活，有可能会出现让生活环境大变样的事情发生。商人梦见骑自行车下坡——预示着你生意将会出现亏损，有可能会因此而损失一大笔钱财。女人梦见骑自行车下坡——预示着你对于现在的感情生活感到迷茫，觉的不能把握对方，感觉对方随时都要离开自己一样。男人梦见骑自行车下坡——预示着你将会换工作，有可能会把你调动在你根本不熟悉的环境中，一切都可能要重头再来。孕妇梦见骑自行车下坡——预示着你要时刻注意自己的身体状况，有可能一些小事情就会影响"},{"id":3506,"keyboard":"喝,酒","title":"梦见喝啤酒","smalltext":"梦见喝啤酒——预示着你会在不想表达意见的场合，装作没听到，或者含糊一点过去比正面回答要好；和恋人发生小摩擦，如果起因不是太严重，就以和好如初的方式结束，带着闷气入睡�

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
