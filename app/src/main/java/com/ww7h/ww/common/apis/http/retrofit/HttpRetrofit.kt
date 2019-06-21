package com.ww7h.ww.common.apis.http.retrofit

import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class HttpRetrofit private constructor() {

    fun <T> initXmlRetrofit(t: Class<T>): T {
        if (retrofits[1] == null) {
            synchronized(HttpRetrofit::class.java) {
                if (retrofits[1] == null) {
                    retrofits[1] = getRetrofit(SimpleXmlConverterFactory.create())
                }
            }

        }
        return retrofits[1]!!.create(t)
    }

    fun <T> initGsonRetrofit(t: Class<T>): T {
        if (retrofits[0] == null) {
            synchronized(HttpRetrofit::class.java) {
                if (retrofits[0] == null) {
                    retrofits[0] = getInstance()!!.getRetrofit(GsonConverterFactory.create())
                }
            }
        }
        return retrofits[0]!!.create(t)
    }

    fun initTextRetrofit(): String {
        if (retrofits[2] == null) {
            synchronized(HttpRetrofit::class.java) {
                if (retrofits[2] == null) {
                    retrofits[2] = getInstance()!!.getRetrofit(GsonConverterFactory.create())
                }
            }
        }
        return retrofits[2]!!.create(String::class.java)
    }

    private fun <T : Converter.Factory> getRetrofit(factory: T): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
            .addConverterFactory(factory) //设置使用Xml解析(记得加入依赖)
            .build()
    }

    companion object {

        private val retrofits = arrayOfNulls<Retrofit>(3)
        @Volatile
        private var instance: HttpRetrofit? = null

        private fun getInstance(): HttpRetrofit? {
            if (instance == null) {
                synchronized(HttpRetrofit::class.java) {
                    if (instance == null) {
                        instance = HttpRetrofit()
                    }
                }
            }
            return instance
        }
    }

}
