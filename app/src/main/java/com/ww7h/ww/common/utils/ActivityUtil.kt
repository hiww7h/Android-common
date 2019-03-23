package com.ww7h.ww.common.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

class ActivityUtil {

    fun findActivity(context: Context): Activity? {
        if (context is Activity) {
            return context
        }
        return if (context is ContextWrapper) {
            findActivity(context.baseContext)
        } else {
            null
        }
    }

}
