package com.example.qicheng.suanming.ui.mine;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.qicheng.suanming.bean.UserModel;
import com.example.qicheng.suanming.utils.CustomDateDialog;
import com.example.qicheng.suanming.utils.CustomDatePicker;
import com.example.qicheng.suanming.utils.GlnlUtils;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.utils.DataCheck;
import com.example.qicheng.suanming.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Response;

public class MineUserActivity extends BaseActivity {

    @BindView(R.id.user_icon)
    LinearLayout user_icon;

    @BindView(R.id.img_usericon)
    ImageView img_usericon;

    @BindView(R.id.user_birth)
    LinearLayout user_birth;

    @BindView(R.id.user_gender)
    LinearLayout user_gender;

    @BindView(R.id.edit_nickname)
    EditText edit_nickname;

    @BindView(R.id.edit_work)
    EditText edit_work;

    @BindView(R.id.text_birth)
    TextView text_birth;

    @BindView(R.id.text_gender)
    TextView text_gender;

    @BindView(R.id.img_birth)
    ImageView img_birth;

    @BindView(R.id.img_gender)
    ImageView img_gender;

    //调用系统相册-选择图片
    private static final int IMAGE = 1;

    private GlnlUtils.glnlType nlType;
    private Boolean isGL = true;

    private int gender = 0; //性别
    private String birthday;
    private String nickName = "";
    private String work = "";
    //    image/png
    private final MediaType MEDIA_TYPE_PNG = MediaType.parse("multipart/form-data");

//    String URL = "http://192.168.2.235:3000/upload";
//    String getUrl = "http://192.168.2.235:3000/get";
//    private File file;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine_user;
    }

    @Override
    protected void initView() {
        showTitleBar();
        setTitleText("账号信息");

        //设置用户头像
        UserModel umodel = Constants.userInfo;
        String nickName = umodel.getNick_name();
        String gender = umodel.getGender();
        String head_img = umodel.getHead_img();
        String u_birthday = umodel.getBirthday();

        text_gender.setText(gender);
        edit_nickname.setText(nickName);

        if (u_birthday != null) {
            birthday = u_birthday;
            text_birth.setText(birthday);
            text_birth.setVisibility(View.VISIBLE);
            img_birth.setVisibility(View.INVISIBLE);
        }

        loadUserIcon(head_img);
    }

    private void loadUserIcon(String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bmp = null;
                try {
                    URL myurl = new URL(url);
                    // 获得连接
                    HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
                    conn.setConnectTimeout(6000);//设置超时
                    conn.setDoInput(true);
                    conn.setUseCaches(false);//不缓存
                    conn.connect();
                    InputStream is = conn.getInputStream();//获得图片的数据流
                    bmp = BitmapFactory.decodeStream(is);//读取图像数据
                    Bitmap finalBmp = bmp;
                    MineUserActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            img_usericon.setImageBitmap(finalBmp);
                        }
                    });

                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void initData() {
        //TODO 设置读写权限
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.user_icon, R.id.user_birth, R.id.user_gender, R.id.btn_modify_userinfo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_icon:
                openSysImage();
                break;
            case R.id.user_birth:
                //弹出日历
                showDatePicker();
                break;
            case R.id.user_gender:  //性别
                //弹出对话框
                showGenderDialog();
                break;
            case R.id.btn_modify_userinfo:  //提交信息按钮
                //弹出对话框
                onClickModifyBtn();
                break;
        }
    }

    /**
     * 修改信息
     */
    public void onClickModifyBtn() {
        //验证信息正确与否
        nickName = edit_nickname.getText().toString().trim();
        work = edit_work.getText().toString().trim();

        if (!DataCheck.isHanzi(nickName)) {
            ToastUtils.showShortToast("请输入正确的昵称,仅支持汉字");
            return;
        }
        if (birthday == null) {
            ToastUtils.showShortToast("请输入正确的生日");
            return;
        }

        //  过滤颜文字
        nickName = DataCheck.filterEmoji(nickName);
        work = DataCheck.filterEmoji(work);


        Map<String, String> map = new HashMap();
        map.put("nick_name", nickName);
        map.put("job", work);
        map.put("birthday", birthday);
        map.put("gender", gender + "");

        OkHttpManager.request(Constants.getApi.UPDATEUSERINFO, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.toString());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    Log.d("jsonObject---->>", jsonObject.getString("msg"));
                    boolean code = jsonObject.getBoolean("code");
                    if (code) {
                        ToastUtils.showShortToast("修改成功");
                        Constants.userInfo.setBirthday(birthday);
                        Constants.userInfo.setGender(gender == 1 ? "男" : "女");
                        Constants.userInfo.setNick_name(nickName);
                        Constants.saveData();
                        finish();
                    } else {
                        String msg = jsonObject.getString("msg");
                        ToastUtils.showShortToast(msg);
                    }
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

    /**
     * 展示男女对话框
     */
    public void showGenderDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(MineUserActivity.this);
        dialog.setTitle("请选择你的性别");
        dialog.setPositiveButton("男",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gender = 1;
                        text_gender.setVisibility(View.VISIBLE);
                        text_gender.setText("男");
                        img_gender.setVisibility(View.INVISIBLE);
                    }
                });
        dialog.setNegativeButton("女",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gender = 2;
                        text_gender.setVisibility(View.VISIBLE);
                        text_gender.setText("女");
                        img_gender.setVisibility(View.INVISIBLE);
                    }
                });
        dialog.setNeutralButton("未知",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gender = 1; //未知默认为男
                        text_gender.setVisibility(View.VISIBLE);
                        text_gender.setText("未知");
                        img_gender.setVisibility(View.INVISIBLE);
                    }
                });
        dialog.show();
    }

    /**
     * 展示日历
     */
    public void showDatePicker() {
        CustomDateDialog dialog = new CustomDateDialog(mContext);
        //设置上下年分限制
        dialog.setYearLimt(50);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
//        dialog.setType(DateType.TYPE_YMD);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd");
        //设置选择回调
        dialog.setOnChangeLisener(new OnChangeLisener() {
            @Override
            public void onChanged(Date date) {
                //日期监听
            }
        });

        //设置点击确定按钮回调
        dialog.setOnSureLisener(new CustomDateDialog.OnCustomSureLisener() {
            //            @Override
//            public void onSure(Date date) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(date);
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH) + 1;  //月份从0开始算起
//                int day = calendar.get(Calendar.DATE);
//
//                String dateStr = year + "-" + month + "-" + day;
//                birthday = dateStr;
//                text_birth.setText(dateStr);
//                text_birth.setVisibility(View.VISIBLE);
//                img_birth.setVisibility(View.INVISIBLE);
//            }
            @Override
            public void onSure(Date date, boolean flag) {
                isGL = flag;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
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
                    birthday = nlDate;
                    text_birth.setText(nlDate);
                    text_birth.setVisibility(View.VISIBLE);
                    img_birth.setVisibility(View.INVISIBLE);
                } else {
                    String dateStr = year + "年" + monthStr + "月" + dayStr + "    " + hourStr + ":" + minuteStr;
                    birthday = dateStr;
                    text_birth.setText(dateStr);
                    text_birth.setVisibility(View.VISIBLE);
                    img_birth.setVisibility(View.INVISIBLE);
                }
            }
        });
        dialog.show();
    }

    public String addZero2Date(int i) {
        String str;
        if (i < 10) {
            str = "0" + i;
        } else {
            str = "" + i;
        }
        return str;
    }

    /**
     * 打开系统相册
     */
    public void openSysImage() {
//        授权
        //调用相册
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("resultCode--->>", resultCode + "");
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            Log.d("showImage--->>", imagePath);

            Constants.localUrl = imagePath;

            File file = new File(imagePath);
            showImage(imagePath, file);
//            sendStudentInfoToServer(file);
//            uploadImage(file);
            c.close();
        }
    }

    //加载图片
    private void showImage(String imaePath, File file) {
        Log.d("showImage--->>", imaePath);
        Bitmap bitmap = BitmapFactory.decodeFile(imaePath);
        img_usericon.setImageBitmap(bitmap);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] datas = baos.toByteArray();
//
//        Map map = new HashMap<String, byte[]>();
//        map.put("image", datas);
//        URL = Constants.getApi.UPDATEUSERICON;

        //传到服务器
        OkHttpManager.uploadImg(Constants.getApi.UPDATEUSERICON, file, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("IOException-->>", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("response-->>", response.body().toString());
                //修改成功 更新头像地址
                String js = response.body().string();
                try {
                    JSONObject json = new JSONObject(js);
                    JSONObject jdata = json.getJSONObject("data");
                    String url = jdata.getString("img_url");
                    Constants.userInfo.setHead_img(url);
                    Constants.saveData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MineUserActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortToast("头像修改成功");
                    }
                });
            }
        });
    }
}
