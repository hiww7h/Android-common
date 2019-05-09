package com.ww7h.ww.common.apis.camera

import android.content.Context
import android.view.View

interface CameraApiInterface {

    interface CameraNeed {

        fun <V : View> init(context: Context, view: V, callBack: CameraCallBack)

        fun openCamera(index: Int)

        fun closeCamera()

        fun addCallbackBuffer(byte: ByteArray);

    }

    interface CameraCallBack {

        fun frameChange(data: ByteArray, width: Int, height: Int)

    }

}
