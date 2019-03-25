package com.ww7h.ww.common.bases.service;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

/**
 * Created by: Android Studio.
 * PackageName: com.ww7h.ww.common.bases.service
 * User: ww
 * DateTime: 2019/3/25 16:32
 */
public interface ServiceInterface {

    /**
     * 绑定service
     * @param context 当前界面service
     * @param intent 绑定携带的参数
     * @param connection 连接回调
     * @param flag 连接标识
     */
    void bindService(Context context, Intent intent, ServiceConnection connection , int flag);

    /**
     * 接触service绑定
     * @param connection 链接
     */
    void unbindService(ServiceConnection connection);

}
