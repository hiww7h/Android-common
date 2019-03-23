package com.ww7h.ww.common.apis.media

import android.content.Context
import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.net.Uri
import android.util.Size
import android.view.Surface
import android.view.TextureView
import com.ww7h.ww.common.utils.ThreadPoolProxy

import java.io.IOException

class VideoPlayer : TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener, MediaInterface.MediaNeed {
    private var mMediaPlayer: MediaPlayer? = null
    private var mContext: Context? = null
    private var mTextureView: TextureView? = null
    private var mSurface: Surface? = null
    private var mPath: String? = null
    private val mMediaCallBack: MediaInterface.MediaCallBack? = null
    private var mSize: Size? = null

    private val mRunnable = Runnable {
        val uri = Uri.parse(mPath)
        try {
            mMediaPlayer!!.setDataSource(mContext!!, uri)
            // 设置渲染画板
            mMediaPlayer!!.setSurface(mSurface)
            // 预加载监听
            mMediaPlayer!!.setOnPreparedListener(this@VideoPlayer)
            // 设置是否保持屏幕常亮
            mMediaPlayer!!.setScreenOnWhilePlaying(true)
            // 同步的方式装载流媒体文件
            mMediaPlayer!!.prepareAsync()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    init {

        mMediaPlayer = MediaPlayer()
    }

    override fun init(textureView: TextureView, context: Context, path: String) {
        mTextureView = textureView
        mContext = context
        mPath = path
        mSize = Size(textureView.layoutParams.width, textureView.layoutParams.height)
    }

    override fun play() {
        mTextureView!!.surfaceTextureListener = this
    }

    override fun pause() {
        mMediaPlayer!!.pause()
    }


    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        mSurface = Surface(surface)
        ThreadPoolProxy.instance.execute(mRunnable)
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {

    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        mSurface = null
        mMediaPlayer!!.stop()
        mMediaPlayer!!.release()
        mMediaPlayer = null
        return true
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {

        if (mMediaCallBack != null) {
            val bitmap = mTextureView!!.getBitmap(1280, 720)
            mMediaCallBack.frameChange(bitmap, mSize!!, surface.timestamp)
        }
    }

    override fun onPrepared(mp: MediaPlayer) {
        mMediaPlayer!!.start()
    }


}
