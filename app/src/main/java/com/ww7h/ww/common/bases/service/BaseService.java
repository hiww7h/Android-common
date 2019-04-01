package com.ww7h.ww.common.bases.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by: Android Studio.
 * PackageName: com.ww7h.ww.common.bases.service
 * DateTime: 2019/3/25 15:24
 * @author ww
 */
public abstract class BaseService<T extends Service> extends Service implements ServiceInterface {

    protected ServiceBinder mBinder = new ServiceBinder();

    /**
     * 获取当前页面的service的实例
     * @return 获取当前service的实例
     */
    protected abstract T getThisService();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class ServiceBinder extends Binder {

        /**
         * 获取当前service
         * @return 返回service实例
         */
        public  T getService() {
            return getThisService();
        }

    }
}
