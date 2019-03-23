package com.ww7h.ww.common.apis.http.okhttp;

import okhttp3.*;
import okio.BufferedSink;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Android Studio.
 * PackgeName com.ww7h.ww.common.apis.http.okhttp
 * User: ww
 * DateTime: 2019/3/22 20:52
 */
public class OkHttpRequestBody extends RequestBody{

    private FormBody.Builder mBuilder;

    OkHttpRequestBody () {
        mBuilder = new FormBody.Builder();
    }

    @Override
    public MediaType contentType() {
        return null;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

    }

    public FormBody.Builder add (String key, String value) {
        mBuilder.add(key, value);
        return mBuilder;
    }

    public FormBody.Builder addMap (Map<String, String> map) {
        for (String key : map.keySet()) {
            mBuilder.add(key, map.get(key));
        }
        return mBuilder;
    }

    public RequestBody build() {
        return mBuilder.build();
    }

}
