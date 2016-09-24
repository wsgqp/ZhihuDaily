package com.demo.ian.zhihu.utils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Admin on 2016-9-8.
 */
public class OkHttpUtil {

    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    public static void GetRequest(String url, Callback callback){
        //创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(callback);
    }
}
