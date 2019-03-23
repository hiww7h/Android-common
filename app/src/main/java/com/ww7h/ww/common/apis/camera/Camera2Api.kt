package com.ww7h.ww.common.apis.camera

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.support.annotation.RequiresApi
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Toast

import java.util.Arrays

class Camera2Api : CameraApiInterface.CameraNeed, SurfaceHolder.Callback {

    private var mSurfaceHolder: SurfaceHolder? = null

    private var mImageReader: ImageReader? = null
    private var mCameraCaptureSession: CameraCaptureSession? = null
    private var mCameraDevice: CameraDevice? = null
    private var mContext: Context? = null
    private var mCameraCallBack: CameraApiInterface.CameraCallBack? = null
    private var mCameraIndex: Int = 0
    private val mWidth = 1280
    private val mHeight = 720

    private var mChildHandler: Handler? = null

    /**
     * 摄像头创建监听
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private val mStateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) { // 打开摄像头
            mCameraDevice = camera
            // 开启预览
            createPreview()
        }

        override fun onDisconnected(camera: CameraDevice) { // 关闭摄像头
            if (null != mCameraDevice) {
                mCameraDevice!!.close()
                mCameraDevice = null
            }
        }

        override fun onError(camera: CameraDevice, error: Int) { // 发生错误
            Toast.makeText(mContext, "摄像头开启失败", Toast.LENGTH_SHORT).show()
        }
    }

    override fun <V : View> init(context: Context, view: V, callBack: CameraApiInterface.CameraCallBack) {
        mContext = context
        mCameraCallBack = callBack

        mSurfaceHolder = (view as SurfaceView).holder
        mSurfaceHolder!!.setKeepScreenOn(true)
    }

    override fun openCamera(index: Int) {
        mCameraIndex = index
        mSurfaceHolder!!.addCallback(this)

    }

    override fun closeCamera() {
        if (mSurfaceHolder != null) {
            mSurfaceHolder!!.removeCallback(this)
        }
        if (null != mCameraDevice) {
            mCameraDevice!!.close()
            mCameraDevice = null
        }

        if (null != mCameraCaptureSession) {
            mCameraCaptureSession!!.close()
            mCameraCaptureSession = null
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        initCamera()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        if (null != mCameraDevice) {
            mCameraDevice!!.close()
            mCameraDevice = null
        }
    }

    @SuppressLint("MissingPermission")
    private fun initCamera() {
        val handlerThread = HandlerThread("Camera2")
        handlerThread.start()
        mChildHandler = Handler(handlerThread.looper)
        val mainHandler = Handler(mContext!!.mainLooper)
        mImageReader = ImageReader.newInstance(
            mWidth, mHeight,
            ImageFormat.YUV_420_888, 1
        )
        mImageReader!!.setOnImageAvailableListener(// 可以在这里处理拍照得到的临时照片 例如，写入本地
        { }, mainHandler)
        val mCameraManager = mContext!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            mCameraManager.openCamera(mCameraIndex.toString(), mStateCallback, mainHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun createPreview() {
        try {
            // 创建预览需要的CaptureRequest.Builder
            val previewRequestBuilder = mCameraDevice!!
                .createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            // 将SurfaceView的surface作为CaptureRequest.Builder的目标
            previewRequestBuilder.addTarget(mSurfaceHolder!!.surface)
            //            previewRequestBuilder.addTarget(mImageReader.getSurface());
            // 创建CameraCaptureSession，该对象负责管理处理预览请求
            mCameraDevice!!.createCaptureSession(
                Arrays.asList<Surface>(mSurfaceHolder!!.surface),
                CCSStateCallback(previewRequestBuilder),
                mChildHandler
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

    }

    internal inner class CCSStateCallback(private val previewRequestBuilder: CaptureRequest.Builder) :
        CameraCaptureSession.StateCallback() {

        override fun onConfigured(session: CameraCaptureSession) {
            if (null == mCameraDevice) {
                return
            }
            //  当摄像头已经准备好时，开始显示预览
            mCameraCaptureSession = session
            try {
                //  自动对焦
                previewRequestBuilder.set(
                    CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
                )
                //  显示预览
                val previewRequest = previewRequestBuilder.build()
                mCameraCaptureSession!!.setRepeatingRequest(previewRequest, null, mChildHandler)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }

        }

        override fun onConfigureFailed(session: CameraCaptureSession) {
            Toast.makeText(mContext, "配置失败", Toast.LENGTH_SHORT).show()
        }
    }
}
