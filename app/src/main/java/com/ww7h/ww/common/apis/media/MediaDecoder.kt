package com.ww7h.ww.common.apis.media

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever

import java.io.File

class MediaDecoder(filePath: String) {
    private var retriever: MediaMetadataRetriever? = null
    /**
     * 取得视频文件播放长度
     * @return
     */
    var videoFileLength: String? = null
        private set

    init {
        val file = File(filePath)

        if (file.exists()) {
            retriever = MediaMetadataRetriever()
            retriever!!.setDataSource(filePath)
            videoFileLength = retriever!!.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        }
    }

    /**
     * 获取视频某一帧
     * @param timeMs 毫秒
     * @param listener
     */
    fun decodeFrame(timeMs: Long, listener: OnGetBitmapListener): Boolean {
        if (retriever == null) return false
        val bitmap = retriever!!.getFrameAtTime(timeMs, MediaMetadataRetriever.OPTION_CLOSEST) ?: return false
        listener.getBitmap(bitmap, timeMs)
        return true
    }

    interface OnGetBitmapListener {
        fun getBitmap(bitmap: Bitmap, timeMs: Long)
    }

    companion object {
        private val TAG = "MediaDecoder"
    }

}


