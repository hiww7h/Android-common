package com.ww7h.ww.common.apis.media

import android.content.Context
import android.graphics.Bitmap
import android.util.Size
import android.view.TextureView

interface MediaInterface {

    interface MediaNeed {
        fun init(textureView: TextureView, context: Context, path: String)
        fun play()
        fun pause()
    }

    interface MediaCallBack {
        fun frameChange(data: Bitmap, size: Size, time: Long)
    }

}
