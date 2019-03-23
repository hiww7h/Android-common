package com.ww7h.ww.common.utils

import java.lang.reflect.Modifier


/**
* Created by ww on 2018/7/24.
*/
object ModelUtil {

    fun modelAttributesExSomeThing(clazz: Class<*>, exclusive:String, fix:String):String{
        return modelAttributes(clazz,exclusive,fix)
    }

    fun modelAttributes(clazz: Class<*>, exclusive:String, fix:String):String {
        var attributes = ""
        clazz.declaredFields
                .filter { (",$exclusive,").indexOf(","+ it.name+",")<0&& Modifier.isPrivate(it.modifiers) }
                .forEach { attributes+=fix+ it.name+"," }
        return if(attributes.isNotEmpty()) attributes.substring(0,attributes.length-1) else ""
    }

}