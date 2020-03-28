package com.qicheng.zhouyi.ui.mine;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.bean.DaShiKeFuBean;
import com.qicheng.zhouyi.bean.UserModel;
import com.qicheng.zhouyi.common.Constants;
import com.qicheng.zhouyi.utils.DonwloadSaveImg;
import com.qicheng.zhouyi.utils.ToastUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.OnClick;

public class MineKeFuActivity extends BaseActivity {

    @BindView(R.id.iv_code)
    ImageView iv_code;

    private Bitmap bitmap;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (bitmap != null) {
                iv_code.setImageBitmap(bitmap);
                try {
                    //保存客服微信二维码
                    DonwloadSaveImg.saveFile(MineKeFuActivity.this, bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    ToastUtils.showShortToast("图片保存失败");
                }
            } else {
                ToastUtils.showShortToast("图片加载失败");
            }
        }
    };

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine_kefu;
    }

    @Override
    protected void initView() {
        showTitleBar();
        setTitleText("客服");
        DaShiKeFuBean kefuInfo = Constants.kefuInfo;
        String head_img = kefuInfo.getKefu_img_url();
        loadUserIcon(head_img);
    }

    private void loadUserIcon(String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL myurl = new URL(url);
                    // 获得连接
                    HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
                    conn.setConnectTimeout(6000);//设置超时
                    conn.setDoInput(true);
                    conn.setUseCaches(false);//不缓存
                    conn.connect();
                    InputStream is = conn.getInputStream();//获得图片的数据流
                    bitmap = BitmapFactory.decodeStream(is);//读取图像数据
                    mHandler.sendEmptyMessage(1);
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

        ToastUtils.showShortToast("复制成功,快去微信加客服小姐姐吧!");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
