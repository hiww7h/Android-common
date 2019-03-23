package com.ww7h.ww.common.utils

import android.content.Context
import android.util.TypedValue

/**
 * Created by ww on 2018/7/29.
 */
object DensityUtil {

    /**
     * @Description TODO(dp转px)
     * @Detailed方法详述(简单方法可不必详述)
     * @param context
     * @param dpVal
     * @return
     * @Author 吴威(wuwei@p2m.com.cn)
     * @Date 2015-8-7 上午10:23:36
     */
    fun dp2px(context: Context, dpVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.resources.displayMetrics).toInt()
    }

    /**
     * @Description TODO(sp转px)
     * @Detailed方法详述(简单方法可不必详述)
     * @param context
     * @param spVal
     * @return
     * @Author 吴威(wuwei@p2m.com.cn)
     * @Date 2015-8-7 上午10:23:54
     */
    fun sp2px(context: Context, spVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.resources.displayMetrics).toInt()
    }

    /**
     * @Description TODO(px转dp)
     * @Detailed方法详述(简单方法可不必详述)
     * @param context
     * @param pxVal
     * @return
     * @Author 吴威(wuwei@p2m.com.cn)
     * @Date 2015-8-7 上午10:24:16
     */
    fun px2dp(context: Context, pxVal: Float): Float {
        val scale = context.resources.displayMetrics.density
        return pxVal / scale
    }

    /**
     * @Description TODO(px转sp)
     * @Detailed方法详述(简单方法可不必详述)
     * @param context
     * @param pxVal
     * @return
     * @Author 吴威(wuwei@p2m.com.cn)
     * @Date 2015-8-7 上午10:24:32
     */
    fun px2sp(context: Context, pxVal: Float): Float {
        return pxVal / context.resources.displayMetrics.scaledDensity
    }
}