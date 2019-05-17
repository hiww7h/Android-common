package com.ww7h.ww.common.bases.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Created by ww on 2018/6/27.
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<T : BaseFragment<T>>:Fragment() {
    protected lateinit var TAG:String

    /**
     * 当前是否使用了设计模式
     */
    protected abstract val designPattern: Boolean

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        TAG = instance.javaClass.name
        return if (designPattern) {
            getContentView(inflater, container)
        } else {
            inflater.inflate(getResourceId(), null)
        }
    }

    fun <V : View> findViewById(id: Int):V? {
        return view?.findViewById(id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initAction()
    }

    /**
     * 返回界面显示视图
     */
    abstract fun getResourceId():Int

    /**
     * 初始化视图
     */
    abstract fun initView()

    /**
     * 初始化事件
     */
    abstract fun initAction()

    /**
     * 当使用MVC、MVP等，设计模式时，需要手动初始化视图
     */
    protected open fun getContentView(inflater: LayoutInflater, container: ViewGroup?): View? {
        return null
    }

    abstract val instance : T
}