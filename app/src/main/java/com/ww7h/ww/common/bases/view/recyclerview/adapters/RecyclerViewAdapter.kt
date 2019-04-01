package com.ww7h.ww.common.bases.view.recyclerview.adapters

import android.support.v7.widget.RecyclerView

abstract class RecyclerViewAdapter<VH :RecyclerViewHolder,T >: RecyclerView.Adapter<VH>() {


    var dataList:ArrayList<T> = ArrayList()


    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, position, getItemViewType(position))
    }

    protected abstract fun onBindViewHolder(holder: VH, position: Int, viewType: Int)

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

    fun getItem(position: Int):T{

        return this.dataList[position]
    }
}