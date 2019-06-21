package com.ww7h.ww.common.bases.broadcast;

import android.content.Context;
import android.content.Intent;

/**
 * Created by: Android Studio.
 * PackageName: com.ww7h.ww.common.bases.broadcast
 * User: ww
 * DateTime: 2019/3/25 16:20
 */
public interface BroadcastReceiverInterface {

    /**
     * 注册广播监听
     * @param context 当前页面的context
     * @param action 接收广播过滤action字段
     * @param callBack 广播回调
     */
    void register(Context context, String action, BroadcastReceiverCallBack callBack);

    /**
     * 注销广播监听
     */
    void unregister();

    interface BroadcastReceiverCallBack {
        /**
         * @param context 页面的context
         * @param intent 传递的intent
         */
        void receiverCallBack(Context context, Intent intent);
    }

}
