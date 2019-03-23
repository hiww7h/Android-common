package com.ww7h.ww.common.utils

import android.content.Context
import java.lang.NullPointerException
import java.lang.ref.WeakReference

class ApplicationContextUtil {

    var applicationContextWeak: WeakReference<Context>? = null

    private object ACUInstance {
        val INSTANCE = ApplicationContextUtil()
    }

    companion object {
        val instance: ApplicationContextUtil
            get() = ACUInstance.INSTANCE
    }

    fun getApplicationContext() : Context {
        return applicationContextWeak?.get() ?:
        throw NullPointerException("应用程序已销毁或未在Application中初始化，不能尝试使用ApplicationContext")
    }

    @Throws(RuntimeException::class)
    fun getName(): String {
        return ""
    }
}
