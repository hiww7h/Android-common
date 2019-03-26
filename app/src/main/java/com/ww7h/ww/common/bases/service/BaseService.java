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
public abstract class BaseService<T extends Service> extends Service implements ServiceInterface, ServiceConnection{

    protected ServiceBinder mBinder = new ServiceBinder();
    protected Context mContext;
    protected ConnectionCallBack mConnectionCallBack;

    /**
     * 获取当前页面的service的实例
     * @return 获取当前service的实例
     */
    protected abstract T getThisService();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void bindService(Context context, Intent intent, ConnectionCallBack connection ,int flag) {
        mContext = context;
        intent.setClass(context, this.getClass());
        context.bindService(intent, this, flag);
    }

    @Override
    public void unbindService(ServiceConnection connection) {
        if (mContext != null) {
            mContext.unbindService(connection);
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        if (mConnectionCallBack != null) {
            mConnectionCallBack.onServiceConnected(name,(ServiceBinder) service);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        if (mConnectionCallBack != null) {
            mConnectionCallBack.onServiceDisconnected(name);
        }
    }

    @Override
    public void onBindingDied(ComponentName name) {

    }

    @Override
    public void onNullBinding(ComponentName name) {

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
