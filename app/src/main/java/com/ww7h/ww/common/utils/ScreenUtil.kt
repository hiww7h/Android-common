package com.ww7h.ww.common.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import java.lang.reflect.Field

class ScreenUtil {

    companion object {
        /**
         * 获取系统状态栏高度
         * @param context
         * @return
         */
         fun getStatusBarHeight(context: Context): Int {
            var c: Class<*>? = null
            var obj: Any? = null
            var field: Field? = null
            var x: Int
            var statusBarHeight = 0
            try {
                c = Class.forName("com.android.internal.R\$dimen")
                obj = c!!.newInstance()
                field = c.getField("status_bar_height")
                x = Integer.parseInt(field!!.get(obj).toString())
                statusBarHeight = context.resources.getDimensionPixelSize(x)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return statusBarHeight
        }


        /**
         * 获得屏幕宽度
         *
         * @param context
         * @return
         */
        fun getScreenWidth(context: Context): Int {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.widthPixels
        }
    }

}