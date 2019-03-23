package com.ww7h.ww.common.bases.view

import android.widget.LinearLayout
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.TextView
import com.ww7h.ww.common.R
import com.ww7h.ww.common.utils.ScreenUtil

class StatusBarColorLayout(context: Context?,var attrs: AttributeSet?) : LinearLayout(context, attrs) {

    init {
        orientation = VERTICAL
        addStatusBar()
    }

    private fun addStatusBar(){
        var statusBar = TextView(context)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.statusBar)
        val color = typedArray.getColor(R.styleable.statusBar_statusBarBgColor, Color.GREEN)
        addView(statusBar)
        statusBar.setBackgroundColor(color)
        var lp = statusBar.layoutParams
        lp.width = ScreenUtil.getScreenWidth(context)
        lp.height = ScreenUtil.getStatusBarHeight(context)
        statusBar.setBackgroundColor(color)
    }

}