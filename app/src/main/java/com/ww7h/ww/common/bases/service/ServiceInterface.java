package com.ww7h.ww.common.bases.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;


/**
 * Created by: Android Studio.
 * PackageName: com.ww7h.ww.common.bases.service
 * User: ww
 * DateTime: 2019/3/25 16:32
 */
public interface ServiceInterface {

    /**
     * 开始
     */
    void startDO();

    /**
     * 停止
     */
    void stopDO();

}
