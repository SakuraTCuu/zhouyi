package com.qicheng.zhouyi.ui.mine;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.bean.DaShiKeFuBean;
import com.qicheng.zhouyi.common.Constants;

import butterknife.BindView;
import butterknife.OnClick;

public class MineKeFuActivity extends BaseActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine_kefu;
    }

    @Override
    protected void initView() {
        showTitleBar();
        setTitleText("客服");
    }

    @OnClick({R.id.btn_kefu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_kefu:
                setClipboard();
                break;
        }
    }

    private void setClipboard() {

        DaShiKeFuBean kefuInfo = Constants.kefuInfo;

        String text = kefuInfo.getKefu_wx();

        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        /**之前的应用过期的方法，clipboardManager.setText(copy);*/
        assert clipboardManager != null;
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, text));
        if (clipboardManager.hasPrimaryClip()) {
            clipboardManager.getPrimaryClip().getItemAt(0).getText();
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
