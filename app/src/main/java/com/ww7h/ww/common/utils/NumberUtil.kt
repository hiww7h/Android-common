package com.ww7h.ww.common.utils

/**
 * Created by ww on 2018/7/12.
 */
object NumberUtil {
    fun Str2Int(str:String):Int{
        return try {
            str.toInt()
        }catch (e:Exception){
            0;
        }
    }

    fun Str2Double(str:String):Double{
        return try {
            str.toDouble()
        }catch (e:Exception){
            0.0;
        }
    }
}