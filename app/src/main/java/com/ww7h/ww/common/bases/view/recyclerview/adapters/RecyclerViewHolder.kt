package com.ww7h.ww.common.bases.view.recyclerview.adapters

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View

abstract class RecyclerViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    fun <T : View> findView(viewId: Int): T {
        return itemView.findViewOften(viewId)
    }

    private fun <T : View> View.findViewOften(viewId: Int): T {
        val viewHolder: SparseArray<View> = tag as? SparseArray<View> ?: SparseArray()
        tag = viewHolder
        var childView: View? = viewHolder.get(viewId)
        if (null == childView) {
            childView = findViewById(viewId)
            viewHolder.put(viewId, childView)
        }
        return childView as T
    }
}