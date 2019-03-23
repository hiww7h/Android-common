package com.ww7h.ww.common.bases

import android.app.Application
import com.ww7h.ww.common.utils.ApplicationContextUtil
import java.lang.ref.WeakReference

abstract class BaseApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        ApplicationContextUtil.instance.applicationContextWeak = WeakReference(this)
    }


}