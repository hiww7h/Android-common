package com.ww7h.ww.common.apis.db

interface GreenDaoCallBack {

    interface QueryCallBack<T> {

        fun querySuccess(t: T)

        fun queryFail(message: String)

    }

}
