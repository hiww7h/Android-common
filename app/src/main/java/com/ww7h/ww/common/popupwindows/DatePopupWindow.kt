package com.ww7h.ww.common.popupwindows

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.ww7h.ww.common.R
import com.ww7h.ww.common.listeners.OnViewClickListener
import com.ww7h.ww.common.utils.ScreenUtil

/**
 * Created by ww on 2018/7/1.
 */
class DatePopupWindow(var context:Context,var sureClickListener: OnViewClickListener<String>):PopupWindow(),View.OnClickListener {

   lateinit var pwDateDp:DatePicker;

    init {
        initWindow()
    }

    @SuppressLint("InflateParams")
    private fun initWindow() {
        val mInflater = LayoutInflater.from(context)
        val datePickerView = mInflater.inflate(R.layout.popup_window_date, null)
        contentView = datePickerView
        height = RelativeLayout.LayoutParams.WRAP_CONTENT
        width = ScreenUtil.getScreenWidth(context)
        this.isFocusable = true
        val dw = ColorDrawable(0x00000000)
        this.setBackgroundDrawable(dw)
        pwDateDp = contentView.findViewById(R.id.pw_date_dp)
        val cancelBtn = contentView.findViewById<Button>(R.id.cancel_btn)
        val sureBtn = contentView.findViewById<Button>(R.id.sure_btn)

        cancelBtn.setOnClickListener(this)
        sureBtn.setOnClickListener(this)
    }

    override fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        super.showAtLocation(parent, gravity, x, y)
        setBackgroundAlpha(0.5f)
    }


    fun setBackgroundAlpha(bgAlpha: Float) {
        val lp = (context as Activity).window.attributes
        lp.alpha = bgAlpha
        (context as Activity).window.attributes = lp
    }

    override fun onClick(v: View?) {
        if(v!!.id == R.id.cancel_btn){
            dismiss()
        }else{
            sureClickListener.onViewClick(getDate())
            dismiss()
        }
    }

    private fun getDate():String{
        var date = ""
        date+=pwDateDp.year
        date+="-"
        if(pwDateDp.month<9){
            date+="0"+(pwDateDp.month+1)
        }else{
            date+=pwDateDp.month+1
        }
        date+="-"
        if(pwDateDp.dayOfMonth<10){
            date+="0"+pwDateDp.dayOfMonth
        }else{
            date+=pwDateDp.dayOfMonth
        }
        return date
    }

}