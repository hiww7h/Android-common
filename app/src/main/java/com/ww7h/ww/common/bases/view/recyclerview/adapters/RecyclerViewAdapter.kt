package com.ww7h.ww.common.bases.view.recyclerview.adapters

import android.os.Handler
import android.os.Message
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import com.ww7h.ww.common.threads.ThreadPoolManager

abstract class RecyclerViewAdapter<VH :RecyclerViewHolder,T >: RecyclerView.Adapter<VH>() {

    var dataList:List<T> = ArrayList()

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, position, getItemViewType(position))
    }

    protected abstract fun onBindViewHolder(holder: VH, position: Int, viewType: Int)

    fun replaceDataList(dataList: List<T>) {
        changeData(dataList)
    }

    fun addDataList(dataList: List<T>) {
        val newDataList = ArrayList<T>()
        newDataList.addAll(this.dataList)
        newDataList.addAll(dataList)
        changeData(newDataList)
    }

    fun addData(data: T) {
        val newDataList = ArrayList<T>()
        newDataList.addAll(this.dataList)
        newDataList.add(data)
        changeData(newDataList)
    }

    fun getItem(position: Int):T{

        return this.dataList[position]
    }

    private fun changeData(newDataList:List<T>) {
        ThreadPoolManager.getInstance().execute {
            val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return dataList.size
                }

                override fun getNewListSize(): Int {
                    return newDataList.size
                }

                override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
                    return areItemsTheSame(dataList[oldPosition], newDataList[newPosition] )
                }

                override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
                    return areContentsTheSame(dataList[oldPosition], newDataList[newPosition] )
                }
            })
            dataList = newDataList
            val message = Message()
            message.what = 0
            message.obj = diffResult
            mHandler.sendMessage(message)
        }
    }

    private var mHandler = Handler(Handler.Callback { msg ->
        val obj: DiffUtil.DiffResult = msg.obj as  DiffUtil.DiffResult
        obj.dispatchUpdatesTo(this)
        false
    })

    protected abstract fun areItemsTheSame(oldM: T, newM: T) : Boolean

    protected abstract fun areContentsTheSame(oldM: T, newM: T) : Boolean

}