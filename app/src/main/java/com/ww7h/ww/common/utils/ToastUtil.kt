package com.ww7h.ww.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.widget.Toast

class ToastUtil private constructor(private val mContext: Context) {

    /**
     * 短时间显示Toast【居下】
     * @param msg 显示的内容-字符串
     */
    fun showShortToast(msg: String) {
        mToast!!.duration = Toast.LENGTH_SHORT
        mToast!!.setText(msg)
        mToast!!.setGravity(Gravity.BOTTOM, 0, DensityUtil.dp2px(mContext, 64f))
        mToast!!.show()
    }

    /**
     * 短时间显示Toast【居中】
     * @param msg 显示的内容-字符串
     */
    fun showShortToastCenter(msg: String) {
        mToast!!.duration = Toast.LENGTH_SHORT
        mToast!!.setText(msg)
        mToast!!.setGravity(Gravity.CENTER, 0, 0)
        mToast!!.show()
    }

    /**
     * 短时间显示Toast【居上】
     * @param msg 显示的内容-字符串
     */
    fun showShortToastTop(msg: String) {
        mToast!!.duration = Toast.LENGTH_SHORT
        mToast!!.setText(msg)
        mToast!!.setGravity(Gravity.TOP, 0, 0)
        mToast!!.show()
    }

    /**
     * 长时间显示Toast【居下】
     * @param msg 显示的内容-字符串
     */
    fun showLongToast(msg: String) {
        mToast!!.duration = Toast.LENGTH_LONG
        mToast!!.setText(msg)
        mToast!!.setGravity(Gravity.BOTTOM, 0, DensityUtil.dp2px(mContext, 64f))
        mToast!!.show()
    }

    /**
     * 长时间显示Toast【居中】
     * @param msg 显示的内容-字符串
     */
    fun showLongToastCenter(msg: String) {
        mToast!!.duration = Toast.LENGTH_LONG
        mToast!!.setText(msg)
        mToast!!.setGravity(Gravity.CENTER, 0, 0)
        mToast!!.show()
    }

    /**
     * 长时间显示Toast【居上】
     * @param msg 显示的内容-字符串
     */
    fun showLongToastTop(msg: String) {
        mToast!!.duration = Toast.LENGTH_LONG
        mToast!!.setText(msg)
        mToast!!.setGravity(Gravity.TOP, 0, 0)
        mToast!!.show()
    }

    companion object {
        private var mToast: Toast? = null
        @SuppressLint("StaticFieldLeak")
        private var mToastUtil: ToastUtil? = null

        fun getInstance(context: Context): ToastUtil {
            if (mToastUtil == null) {
                synchronized(ToastUtil::class.java) {
                    if (mToastUtil == null) {
                        mToastUtil = ToastUtil(context)
                        mToast = Toast(context)
                    }
                }
            }
            mToast!!.cancel()
            return this.mToastUtil!!
        }
    }


}
