package com.ww7h.ww.common.apis.http.retrofit

interface RequestCallBack {

    interface HttpPostCallBack<T> {
        fun postSuccess(t: T)

        fun postFail(t: T)
    }

}
