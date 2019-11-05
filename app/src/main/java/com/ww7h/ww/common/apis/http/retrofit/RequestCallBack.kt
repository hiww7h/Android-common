package com.ww7h.ww.common.apis.http.retrofit

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded



interface RequestCallBack {

    interface HttpPostCallBack<T> {
        fun postSuccess(t: T)

        fun postFail(t: T)
    }


    @FormUrlEncoded
    @POST("user/personal_list_info")
    fun getPersonalListInfo(@Field("cur_page") page: Int): Call<Response<String>>


}


