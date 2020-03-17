package com.qicheng.zhouyi.common;

import android.util.Log;

import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OkHttpManager {

    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Request.Builder builder = new Request.Builder();

    public interface RequestListener {
        void Success(HttpInfo info);

        void Fail(HttpInfo info);
    }

    public static void request(String url, int type, Map<String, String> params, RequestListener requestListener) {
        Log.d("OkHttp.request-->>>", "params-->>" + params.toString());

        OkHttpUtil.getDefault().doAsync(HttpInfo.Builder()
                        .setUrl(url)
                        .setRequestType(type)//设置请求方式
                        .addParam("user_id","48")
                        .addParams(params)
                        .build(),
                new com.okhttplib.callback.Callback() {
                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        Log.d("onSuccess--->", info.getRetDetail());
                        requestListener.Success(info);
                    }

                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        Log.d("onFailure--->", info.getRetDetail());
                        requestListener.Fail(info);
                    }
                });
    }

    /**
     * 发送OkHttp GET 请求的方法
     *
     * @param url      url形式参数
     * @param callback 回调
     */
    public static void sendOkHttpGetRequest(String url, Callback callback) {
        Request request = builder.url(url).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 发送OkHttp POST 请求的方法
     *
     * @param urlAddress  url地址
     * @param requestBody 请求体
     * @param callback    回调
     */
    public static void sendOkHttpPostRequest(String urlAddress, RequestBody requestBody, Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(urlAddress).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void uploadImg(String URL, File file, Callback callback) {
        MediaType MEDIA_TYPE_PNG = MediaType.parse("multipart/form-data");
        //2.创建RequestBody
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, file);

        //3.构建MultipartBody
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "testImage.png", fileBody)
                .addFormDataPart("user_id", "48")
                .build();
        //4.构建请求
        //传到服务器
        Request request = new Request.Builder().url(URL).post(requestBody).build();
        //5.发送请求
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void sendMultipart(String urlAddress, RequestBody requestBody, Callback callback) {
        //这里根据需求传，不需要可以注释掉
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("title", "wangshu")
//                .addFormDataPart("image", "wangshu.jpg",
//                        RequestBody.create(MEDIA_TYPE_PNG, new File("/sdcard/wangshu.jpg")))
//                .build();
//        private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//        .addHeader("","e5cHLWScbto3VfvYTU1llVZgl/WniA4QZZ8epmn8k/o=")
        Request request = new Request.Builder().url(urlAddress).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
