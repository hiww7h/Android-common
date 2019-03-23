package com.ww7h.ww.common.bases.view.recyclerview.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ww7h.ww.common.R
import java.util.ArrayList

abstract class LoadMoreAdapter<VH : RecyclerViewHolder,T> (var context: Context): RecyclerView.Adapter<VH>() {

    private var dataList: ArrayList<T> = ArrayList()

    abstract fun getViewType(position: Int):Int

    override fun getItemViewType(position: Int): Int {
        return if (position < itemCount - 1 || itemCount < getPageCount()) {
            getViewType(position)
        } else 100
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return if (viewType == 100) {
            LoadMoreViewHolder(LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false)) as VH
        } else {
            onCreateViewHolder(parent, viewType, 1)
        }
    }

    protected abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int, position: Int): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (getItemViewType(position) != 100) {
            onBindViewHolder(holder , position, getItemViewType(position))
        }
    }

    protected abstract fun onBindViewHolder(holder: VH, position: Int, viewType: Int)


    fun getItem(position: Int): T {
        return dataList[position]
    }

    override fun getItemCount(): Int {
        return if (dataList.size < getPageCount()) dataList.size else dataList.size + 1
    }

    fun replaceDataList(dataList: List<T>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun addDataList(dataList: List<T>) {
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun addData(data: T) {
        dataList.add(data)
        notifyDataSetChanged()
    }

    fun getDataList(): List<T> {
        return dataList
    }

    protected fun getPageCount() : Int {
        return 10
    }

    inner class LoadMoreViewHolder(itemView: View) : RecyclerViewHolder(itemView)
}