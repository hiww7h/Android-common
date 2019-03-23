package com.ww7h.ww.common.apis.camera

import android.app.Activity
import android.content.Context
import android.graphics.ImageFormat
import android.hardware.Camera
import android.util.Size
import android.view.*

import java.io.IOException

class Camera1Api : CameraApiInterface.CameraNeed, SurfaceHolder.Callback, Camera.PreviewCallback {

    private var mContext: Context? = null
    private var mSurfaceView: SurfaceView? = null
    private var mCallBack: CameraApiInterface.CameraCallBack? = null
    private var mCamera: Camera? = null
    private var mCameraIndex: Int = 0
    /**
     * 预览尺寸
     */
    private val mSize = Size(1280, 720)
    private var mCameraOpen = false

    private val cameraInstance: Camera?
        get() {
            var c: Camera? = null
            try {
                c = Camera.open(mCameraIndex)
                val p = c!!.parameters
                p.previewFormat = ImageFormat.NV21
                val imageWidth = mSize.width
                val imageHeight = mSize.height
                p.setPreviewSize(imageWidth, imageHeight)
                p.pictureFormat = ImageFormat.NV21
                p.setPictureSize(imageWidth, imageHeight)
                p.zoom = 0
                val frameRate = 30
                p.previewFrameRate = frameRate
                val cameraRotation = 0
                p.setRotation(cameraRotation)
                c.parameters = p
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return c
        }

    // 获取当前窗口管理器显示方向
    private val displayOrientation: Int
        get() {
            val windowManager = (mContext as Activity).windowManager
            val display = windowManager.defaultDisplay
            val rotation = display.rotation
            var degrees = 0
            when (rotation) {
                Surface.ROTATION_0 -> {
                }
                Surface.ROTATION_90 -> degrees = 90
                Surface.ROTATION_180 -> degrees = 180
                Surface.ROTATION_270 -> degrees = 270
            }

            val camInfo = Camera.CameraInfo()
            Camera.getCameraInfo(
                Camera.CameraInfo.CAMERA_FACING_BACK, camInfo
            )
            return (camInfo.orientation - degrees + 360) % 360
        }


    override fun <V : View> init(context: Context, view: V, callBack: CameraApiInterface.CameraCallBack) {

        mCallBack = callBack
        mContext = context
        mSurfaceView = view as SurfaceView

    }

    override fun openCamera(index: Int) {

        mCameraIndex = index
        mSurfaceView!!.holder.addCallback(this)

    }

    override fun closeCamera() {
        if (mSurfaceView != null) {
            mSurfaceView!!.holder.removeCallback(this)
        }
        if (mCamera != null) {
            mCamera!!.setPreviewCallback(null)
            mCamera!!.stopPreview()
            mCamera!!.release()
            mCamera = null
            mCameraOpen = false
        }
    }


    override fun surfaceCreated(holder: SurfaceHolder) {
        mCamera = cameraInstance
        try {
            mCamera!!.setPreviewDisplay(holder)
            mCamera!!.startPreview()
            mCamera!!.setPreviewCallback(this)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        val rotation = displayOrientation //获取当前窗口方向
        mCamera!!.setDisplayOrientation(rotation) //设定相机显示方向
        try {
            if (null != mCamera) {
                mCamera!!.setPreviewDisplay(holder)
                if (mCameraOpen) {
                    mCamera!!.startPreview()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        holder?.removeCallback(this)
    }

    override fun onPreviewFrame(data: ByteArray, camera: Camera) {
        mCallBack!!.frameChange(
            data, camera.parameters.pictureSize.width,
            camera.parameters.pictureSize.height
        )
    }
}
