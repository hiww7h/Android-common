package com.ww7h.ww.common.bases

import android.app.Application
import com.ww7h.ww.common.R
import com.ww7h.ww.common.threads.ThreadPoolManager
import com.ww7h.ww.common.utils.ApplicationContextUtil
import com.ww7h.ww.common.utils.DateUtil
import com.ww7h.ww.common.utils.FileUtil
import java.lang.ref.WeakReference

abstract class BaseApplication : Application(), Thread.UncaughtExceptionHandler {

    override fun onCreate() {
        super.onCreate()

        ApplicationContextUtil.instance.applicationContextWeak = WeakReference(this)
//        Thread.setDefaultUncaughtExceptionHandler(this)

    }

    override fun uncaughtException(t: Thread?, e: Throwable?) {

//        ThreadPoolManager.getInstance().execute {
//            FileUtil.createTextFile(t.toString() + "\n" + e.toString(),
//                "/ww7h/" + resources.getString(R.string.app_name),
//                "app_error_file" + DateUtil.longToDateStr(System.currentTimeMillis(),
//                    "yyyy-mm-dd HH:MM:ss") + ".txt" )
//        }

    }

}