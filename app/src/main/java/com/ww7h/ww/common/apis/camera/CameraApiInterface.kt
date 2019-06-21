package com.ww7h.ww.common.apis.camera

import android.content.Context
import android.view.View

interface CameraApiInterface {

    companion object{
        const val CALL_BACK_MANUAL:Int = 0x10001;
        const val CALL_BACK_AUTO:Int = 0x10002;
    }

    interface CameraNeed {

        fun <V : View> init(context: Context, view: V, callBack: CameraCallBack)

        fun <V : View> init(context: Context, view: V, callBack: CameraCallBack, callBackType:Int)

        fun openCamera(index: Int)

        fun closeCamera()

        fun addCallbackBuffer(byte: ByteArray);

    }

    interface CameraCallBack {

        fun frameChange(data: ByteArray, width: Int, height: Int)

    }

}
