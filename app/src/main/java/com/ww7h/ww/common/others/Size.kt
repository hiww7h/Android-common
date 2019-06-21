package com.ww7h.ww.common.others

/**
 * ================================================
 * 描述：
 * 来源：     Android Studio.
 * 项目名：   Android-common
 * 包名：     com.ww7h.ww.common.others
 * 创建时间：  2019/5/16 11:50
 * @author   ww  Github地址：https://github.com/ww7hcom
 * ================================================
 */
class Size<T> {

    var width: T? = null
    var height: T? = null

    internal constructor(w: T, h: T) {
        width = w
        height = h
    }

    internal constructor() {}

}