package com.ww7h.ww.common.popupwindows.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ww7h.ww.common.R
import com.ww7h.ww.common.bases.view.recyclerview.adapters.RecyclerViewAdapter
import com.ww7h.ww.common.bases.view.recyclerview.adapters.RecyclerViewHolder
import com.ww7h.ww.common.listeners.OnRecyclerItemClick
import com.ww7h.ww.common.popupwindows.models.TypeModel

class TypeAdapter(var context: Context,var onRecyclerItemClick: OnRecyclerItemClick<TypeModel>):

    RecyclerViewAdapter<TypeAdapter.TypeViewHolder, TypeModel>() {
    override fun areItemsTheSame(oldM: TypeModel, newM: TypeModel): Boolean {
        return oldM.typeName == newM.typeName || oldM.typeTag == newM.typeTag
    }

    override fun areContentsTheSame(oldM: TypeModel, newM: TypeModel): Boolean {
        return oldM.typeName == newM.typeName || oldM.typeTag == newM.typeTag
    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int, viewType: Int) {
        holder.type_tv.text = getItem(position).typeName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        return TypeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_type,parent,false))
    }


    inner class TypeViewHolder(itemView:View): RecyclerViewHolder(itemView),View.OnClickListener{


        var type_tv = findView<TextView>(R.id.type_tv)
        init {
            type_tv.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            onRecyclerItemClick.onItemClick(getItem(adapterPosition))
        }
    }
}