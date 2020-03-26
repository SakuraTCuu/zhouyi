package com.qicheng.zhouyi.ui.mine;

import android.widget.TextView;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseActivity;

import butterknife.BindView;

public class MineAboutMeActivity extends BaseActivity {

    @BindView(R.id.tv_about)
    TextView tv;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine_aboutme;
    }

    @Override
    protected void initView() {

        String text = "您好，欢迎使用周易命理大师app！中华周易术数，从远古的龟卜、蓍占；到汉唐以来的周易八卦、八字算命、六爻算卦、梅花数；以及民间流传的称骨算命、抽签、测名、解梦等，同时亦有从西方传入的星座运程、塔罗牌占卜等占法，可谓流派繁多，蔚为大观。\n" +
                " 　　而一个人的姓名，则体现了其家族流传及特定的精神内涵。周易专家结合五行笔划数理等进行系统研究，使姓名学发展为一派重要的预测学术，风行海内，成为人们化灾改运的必备之法。\n" +
                " 　　中华预测学源远流长，玄奥幽深，分析和测算起来极为繁复，令我等凡夫俗子望而生畏，不知从何着手。如今有了『周易命理大师app』，占卜命运就轻而易举了，您不用翻书求人，很容易就能全方位进行八字算命、财运分析、姻缘测试、起名改名等各项占卜！";

        showTitleBar();
        setTitleText("关于我们");

        tv.setText(text);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
