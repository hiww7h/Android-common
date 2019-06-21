package com.ww7h.ww.common.bases.view.glsuerfaceview;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;

public class YuvGLRenderer implements Renderer {
    private GLSurfaceView mTargetSurface;
    private YuvGLProgram prog = new YuvGLProgram(0);
    private int mScreenWidth;
    private int mScreenHeight;
    private int mVideoWidth;
    private int mVideoHeight;
    private ByteBuffer y;
    private ByteBuffer u;
    private ByteBuffer v;
    private boolean    mHasData = false;

    public YuvGLRenderer(GLSurfaceView surface, int videoWidth, int videoHeight) {
        mTargetSurface = surface;
        mVideoWidth = videoWidth;
        mVideoHeight = videoHeight;

        mTargetSurface.setEGLContextClientVersion(2);
        mTargetSurface.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        mTargetSurface.setRenderer(this);
        mTargetSurface.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        if (!prog.isProgramBuilt()) {
            prog.buildProgram();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mScreenWidth = width;
        mScreenHeight = height;
        setVideoParam(mVideoWidth, mVideoHeight);
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        synchronized (this) {
            if (y != null && mHasData) {
                // reset position, have to be done
                y.position(0);
                u.position(0);
                v.position(0);
                prog.buildTextures(y, u, v, mVideoWidth, mVideoHeight);
                // GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                // GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
                prog.drawFrame();
            } else {
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            }
        }
    }

    /**
     * this method will be called from native code, it happens when the video is about to play or
     * the video size changes.
     */
    public void setVideoParam(int w, int h) {
        if (w > 0 && h > 0) {
            // 调整比例
            if (mScreenWidth > 0 && mScreenHeight > 0) {
//                float f1 = 1f * mScreenHeight / mScreenWidth;
//                float f2 = 1f * h / w;
//                if (f1 == f2) {
                prog.createBuffers(YuvGLProgram.squareVertices);
//                } else if (f1 < f2) {
//                    float widScale = f1 / f2;
//                    prog.createBuffers(new float[] { -widScale, -1.0f, widScale, -1.0f, -widScale, 1.0f, widScale,
//                            1.0f, });
//                } else {
//                    float heightScale = f2 / f1;
//                    prog.createBuffers(new float[] { -1.0f, -heightScale, 1.0f, -heightScale, -1.0f, heightScale, 1.0f,
//                            heightScale, });
//                }
            }
            // 初始化容器
            if (w != mVideoWidth && h != mVideoHeight || null == y) {
                this.mVideoWidth = w;
                this.mVideoHeight = h;
                int yarraySize = w * h;
                int uvarraySize = yarraySize / 4;
                synchronized (this) {
                    y = ByteBuffer.allocate(yarraySize);
                    u = ByteBuffer.allocate(uvarraySize);
                    v = ByteBuffer.allocate(uvarraySize);
                }
            }
        }
    }

    /**
     * this method will be called from native code, it's used for passing yuv data to me.
     */
    public void update(byte[] ydata, byte[] udata, byte[] vdata) {
        if (null == y) {
            return;
        }

        synchronized (this) {
            y.clear();
            u.clear();
            v.clear();
            y.put(ydata, 0, ydata.length);
            u.put(udata, 0, udata.length);
            v.put(vdata, 0, vdata.length);
        }

        // request to render
        mHasData = true;
        mTargetSurface.requestRender();
    }

    public void update(byte[] data, int width, int height) {
        if (null == y) {
            return;
        }

        synchronized (this) {
            y.clear();
            u.clear();
            v.clear();
            y.put(data, 0, width * height);
            u.put(data, width * height, width * height / 4);
            v.put(data, width * height * 5 / 4, width * height / 4);
        }

        // request to render
        mHasData = true;
        mTargetSurface.requestRender();
    }

    public void nv21Update(byte[] data, int width, int height) {

        int pixelLength = width * height;
        byte[] yData = new byte[pixelLength];
        byte[] uData = new byte[pixelLength / 4];
        byte[] vData = new byte[pixelLength / 4];
        System.arraycopy(data, 0, yData, 0, pixelLength);
        for (int i = 0; i < pixelLength / 4; i++) {
            vData[i] = data[pixelLength + i * 2];
            uData[i] = data[pixelLength + i * 2 + 1];
        }

        if (null == y) {
            return;
        }

        synchronized (this) {
            y.clear();
            u.clear();
            v.clear();
            y.put(yData, 0, yData.length);
            u.put(uData, 0, uData.length);
            v.put(vData, 0, vData.length);
        }

        // request to render
        mHasData = true;
        mTargetSurface.requestRender();

    }

}
