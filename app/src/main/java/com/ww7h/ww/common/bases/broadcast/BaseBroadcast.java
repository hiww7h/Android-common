package com.ww7h.ww.common.bases.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by: Android Studio.
 * PackageName: com.ww7h.ww.common.bases.broadcast
 * User: ww
 * DateTime: 2019/3/25 16:04
 */
public class BaseBroadcast extends BroadcastReceiver implements BroadcastReceiverInterface {

    private Context mContext;
    private BroadcastReceiverCallBack mCallBack;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mCallBack != null) {
            mCallBack.receiverCallBack(context, intent);
        }
    }

    @Override
    public void register(Context context, String action, BroadcastReceiverCallBack callBack) {
        mContext = context;
        mCallBack = callBack;
        IntentFilter filter = new IntentFilter();
        filter.addAction(action);
        context.registerReceiver(this, filter);
    }

    @Override
    public void unregister() {
        if (mContext != null) {
            mContext.unregisterReceiver(this);
        }
    }


}
