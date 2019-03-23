package com.ww7h.ww.common.apis.http.okhttp;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * OkHttp类
 * Created by: Android Studio.
 * PackageName: com.ww7h.ww.common.apis.http.okhttp
 * User: ww
 * DateTime: 2019/3/22 20:49
 */
public class HttpOkHttp {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client;

    private HttpOkHttp () {
        client = new OkHttpClient();
    }

    private static class HttpOkHttpInstance {
        private final static HttpOkHttp INSTANCE = new HttpOkHttp();
    }

    public static HttpOkHttp getInstance() {
        return HttpOkHttpInstance.INSTANCE;
    }

    /**
     * GET请求
     * @param url 请求地址
     * @param okHttpCallBack 请求回调
     * @param clazz 返回结果的Class
     * @param <T> 返回结果类型
     */
    public <T> void requestGet(@NotNull String url, @NotNull final OkHttpCallBack<T> okHttpCallBack,
                               @NotNull final Class<T> clazz) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                okHttpCallBack.requestFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    requestResult(response.body().string(), okHttpCallBack, clazz);
                } else {
                    okHttpCallBack.requestFailure(response.message());
                }
            }
        });
    }

    /**
     * POST请求
     * @param url 请求地址
     * @param json 请求参数 json 格式
     * @param okHttpCallBack 请求回调
     * @param clazz 返回结果的class
     * @param <T> 请求返回的类型
     */
    public <T> void requestPost(@NotNull String url, @NotNull String json, @NotNull final OkHttpCallBack<T> okHttpCallBack,
                         @NotNull final Class<T> clazz) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                okHttpCallBack.requestFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    requestResult(response.body().string(), okHttpCallBack, clazz);
                } else {
                    okHttpCallBack.requestFailure(response.message());
                }
            }
        });
    }

    private <T> void requestResult(String result, OkHttpCallBack<T> callBack, @NotNull Class<T> clazz) {

        if ("java.lang.String".equals(clazz.getName())) {
            callBack.requestSuccess((T) result);

        } else {
            Gson gson = new GsonBuilder().create();
            callBack.requestSuccess(gson.fromJson(result, clazz));
        }
    }


    public interface OkHttpCallBack<T> {
        /**
         * 请求成功回调
         * @param t 回调返回成功结果输出
         */
        void requestSuccess (T t);

        /**
         * 请求失败回调
         * @param message 回调返回失败消息
         */
        void requestFailure (String message);
    }

}
