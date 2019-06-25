package com.ww7h.ww.common.utils

import android.content.Context
import android.content.SharedPreferences
import android.support.v4.content.SharedPreferencesCompat
import org.apache.commons.codec.binary.Base64
import java.io.*
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

object SPUtil {
    /**
     * 保存在手机里面的文件名
     */
    private const val FILENAME = "share_data"

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    fun put(context: Context, key: String, value: Any) {

        val sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
        val editor = sp.edit()

        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
            else -> saveObject(editor, key, value)
        }
        SharedPreferencesCompat.apply(editor)
    }

    private fun saveObject(editor: SharedPreferences.Editor, key: String, value: Any) {
        // 创建字节输出流
        val baos = ByteArrayOutputStream()
        try {
            // 创建对象输出流，并封装字节流
            val oos = ObjectOutputStream(baos)
            // 将对象写入字节流
            oos.writeObject(value)
            // 将字节流编码成base64的字符窜
            val oAuthBase64 = String(Base64.encodeBase64(baos.toByteArray()))
            editor.putString(key, oAuthBase64)
            editor.commit()
        } catch (e: IOException) {

        }

    }

    fun readObject(sp: SharedPreferences, key: String): Any? {
        var obj: Any? = null
        val productBase64 = sp.getString(key, "")

        //读取字节
        val base64 = Base64.decodeBase64(productBase64!!.toByteArray())

        //封装到字节流
        val bais = ByteArrayInputStream(base64)
        try {
            //再次封装
            val bis = ObjectInputStream(bais)
            try {
                //读取对象
                obj = bis.readObject()
            } catch (e: ClassNotFoundException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

        } catch (e: StreamCorruptedException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return obj
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    fun get(context: Context, key: String, defaultObject: Any): Any? {
        val sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
        return if (defaultObject is String) {
            sp.getString(key, defaultObject)
        } else if (defaultObject is Int) {
            sp.getInt(key, defaultObject)
        } else if (defaultObject is Boolean) {
            sp.getBoolean(key, defaultObject)
        } else if (defaultObject is Float) {
            sp.getFloat(key, defaultObject)
        } else if (defaultObject is Long) {
            sp.getLong(key, defaultObject)
        } else {
            readObject(sp, key)
        }
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    fun remove(context: Context, key: String) {
        val sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove(key)
        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    fun clear(context: Context) {
        val sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.clear()
        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    fun contains(context: Context, key: String): Boolean {
        val sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
        return sp.contains(key)
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    fun getAll(context: Context): Map<String, *> {
        val sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
        return sp.all
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private object SharedPreferencesCompat {
        private val sApplyMethod = findApplyMethod()

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        private fun findApplyMethod(): Method? {
            try {
                val clz = SharedPreferences.Editor::class.java
                return clz.getMethod("apply")
            } catch (e: NoSuchMethodException) {
            }

            return null
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        fun apply(editor: SharedPreferences.Editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor)
                    return
                }
            } catch (e: IllegalArgumentException) {
            } catch (e: IllegalAccessException) {
            } catch (e: InvocationTargetException) {
            }

            editor.commit()
        }
    }
}



