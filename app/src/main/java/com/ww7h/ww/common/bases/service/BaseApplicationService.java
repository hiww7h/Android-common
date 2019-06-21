package com.ww7h.ww.common.bases.service;

import android.app.Service;
import android.content.Intent;

/**
 * Created by: Android Studio.
 * Project Nam: Android-common
 * PackageName: com.ww7h.ww.common.bases.service
 * DateTime: 2019/3/29 19:11
 *
 * @author ww
 */
public abstract class BaseApplicationService<T extends Service> extends BaseService<T> {

    /**
     * 如果service没被创建过，调用startService()后会执行onCreate()回调；
     * 如果service已处于运行中，调用startService()不会执行onCreate()方法。
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 如果多次执行了Context的startService()方法，那么Service的onStartCommand()方法也会相应的多次调用。
     * onStartCommand()方法很重要，我们在该方法中根据传入的Intent参数进行实际的操作
     * @param intent 传递
     * @param flags START_FLAG_REDELIVERY： 如果你实现onStartCommand()来安排异步工作或者在另一个线程中工作, 那么你可能需要使用START_FLAG_REDELIVERY来 让系统重新发送一个intent。这样如果你的服务在处理它的时候被Kill掉, Intent不会丢失.
     *              START_FLAG_RETRY：表示服务之前被设为START_STICKY，则会被传入这个标记。
     * @param startId
     * @return 1):START_STICKY： 如果service进程被kill掉，保留service的状态为开始状态，但不保留递送的intent对象。随后系统会尝试重新创建service，由 于服务状态为开始状态，所以创建服务后一定会调用onStartCommand(Intent,int,int)方法。如果在此期间没有任何启动命令被传 递到service，那么参数Intent将为null。
     *         2):START_NOT_STICKY：“非粘性的”。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统不会自动重启该服务
     *         3):START_REDELIVER_INTENT：重传Intent。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统会自动重启该服务，并将Intent的值传入。
     *         4):START_STICKY_COMPATIBILITY：START_STICKY的兼容版本，但不保证服务被kill后一定能重启。
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
