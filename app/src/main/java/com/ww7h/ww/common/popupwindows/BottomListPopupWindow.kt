package com.ww7h.ww.common.popupwindows

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.ww7h.ww.common.R
import com.ww7h.ww.common.bases.view.recyclerview.decoration.SpaceItemDecoration
import com.ww7h.ww.common.listeners.OnRecyclerItemClick
import com.ww7h.ww.common.popupwindows.adapters.TypeAdapter
import com.ww7h.ww.common.popupwindows.models.TypeModel
import com.ww7h.ww.common.utils.DensityUtil
import com.ww7h.ww.common.utils.ScreenUtil

class BottomListPopupWindow(var context: Context, typeList:List<TypeModel>, private var onRecyclerItemClick: OnRecyclerItemClick<TypeModel>):PopupWindow(),View.OnClickListener,OnRecyclerItemClick<TypeModel> {

    private var typeAdapter: TypeAdapter = TypeAdapter(context,this)

    init {
        contentView = LayoutInflater.from(context).inflate(R.layout.popup_window_bottom_list,null)
        val cancelBtn = contentView.findViewById<Button>(R.id.cancel_btn)
        val selectListRv = contentView.findViewById<RecyclerView>(R.id.select_list_rv)
        selectListRv.layoutManager = LinearLayoutManager(context)
        selectListRv.adapter = typeAdapter
        selectListRv.addItemDecoration( SpaceItemDecoration(DensityUtil.dp2px(context ,1f),1))

        typeAdapter.replaceDataList(typeList)
        height = RelativeLayout.LayoutParams.WRAP_CONTENT
        width = ScreenUtil.getScreenWidth(context)
        this.isFocusable = true
        val dw = ColorDrawable(0x00000000)
        this.setBackgroundDrawable(dw)

        cancelBtn.setOnClickListener(this)
    }

    override fun onItemClick(t: TypeModel?) {
        onRecyclerItemClick.onItemClick(t!!)
        dismiss()
    }

    fun updateTypeList(typeList:List<TypeModel>){
        typeAdapter.replaceDataList(typeList)
    }

    override fun onClick(p0: View?) {

        dismiss()
    }

    override fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        super.showAtLocation(parent, gravity, x, y)
        setBackgroundAlpha(0.5f)
    }

    private fun setBackgroundAlpha(bgAlpha: Float) {
        val lp = (context as Activity).window.attributes
        lp.alpha = bgAlpha
        (context as Activity).window.attributes = lp
    }

    override fun dismiss() {
        setBackgroundAlpha(1f)
        super.dismiss()
    }

}