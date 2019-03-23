package com.ww7h.ww.common.bases.view.glsuerfaceview;

import android.opengl.GLES20;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * step to use:<br/>
 * 1. new GLProgram()<br/>
 * 2. buildProgram()<br/>
 * 3. buildTextures()<br/>
 * 4. drawFrame()<br/>
 */
public class YuvGLProgram {

    // program id
    private int mProgram;
    // window position
    public final int mWinPosition;
    // texture id
    private int mTextureI;
    private int mTextureII;
    private int mTextureIII;
    // texture index in gles
    private int mIndexI;
    private int mIndexII;
    private int mIndexIII;
    // vertices on screen
    private float[] mVertices;
    // handles
    private int mPositionHandle = -1;
    private int mCoordHandle = -1;
    private int mHandleY = -1;
    private int mHandleU = -1;
    private int mHandleV = -1;
    private int mTidY = -1;
    private int mTidU = -1;
    private int mTidV = -1;
    // vertices buffer
    private ByteBuffer mVertexBuffer;
    private ByteBuffer mCoordBuffer;
    // video width and height
    private int mVideoWidth = -1;
    private int mVideoHeight = -1;
    // flow control
    private boolean mIsProgBuilt = false;

    /**
     * position can only be 0~4:<br/>
     * fullscreen => 0<br/>
     * left-top => 1<br/>
     * right-top => 2<br/>
     * left-bottom => 3<br/>
     * right-bottom => 4
     */
    public YuvGLProgram(int position) {
        if (position < 0 || position > 4) {
            throw new RuntimeException("Index can only be 0 to 4");
        }
        mWinPosition = position;
        setup(mWinPosition);
    }

    /**
     * prepared for later use
     */
    public void setup(int position) {
        switch (mWinPosition) {
            case 1:
                mVertices = squareVertices1;
                mTextureI = GLES20.GL_TEXTURE0;
                mTextureII = GLES20.GL_TEXTURE1;
                mTextureIII = GLES20.GL_TEXTURE2;
                mIndexI = 0;
                mIndexII = 1;
                mIndexIII = 2;
                break;
            case 2:
                mVertices = squareVertices2;
                mTextureI = GLES20.GL_TEXTURE3;
                mTextureII = GLES20.GL_TEXTURE4;
                mTextureIII = GLES20.GL_TEXTURE5;
                mIndexI = 3;
                mIndexII = 4;
                mIndexIII = 5;
                break;
            case 3:
                mVertices = squareVertices3;
                mTextureI = GLES20.GL_TEXTURE6;
                mTextureII = GLES20.GL_TEXTURE7;
                mTextureIII = GLES20.GL_TEXTURE8;
                mIndexI = 6;
                mIndexII = 7;
                mIndexIII = 8;
                break;
            case 4:
                mVertices = squareVertices4;
                mTextureI = GLES20.GL_TEXTURE9;
                mTextureII = GLES20.GL_TEXTURE10;
                mTextureIII = GLES20.GL_TEXTURE11;
                mIndexI = 9;
                mIndexII = 10;
                mIndexIII = 11;
                break;
            case 0:
            default:
                mVertices = squareVertices;
                mTextureI = GLES20.GL_TEXTURE0;
                mTextureII = GLES20.GL_TEXTURE1;
                mTextureIII = GLES20.GL_TEXTURE2;
                mIndexI = 0;
                mIndexII = 1;
                mIndexIII = 2;
                break;
        }
    }

    public boolean isProgramBuilt() {
        return mIsProgBuilt;
    }

    public void buildProgram() {
        if (mProgram <= 0) {
            mProgram = createProgram(VERTEX_SHADER, FRAGMENT_SHADER);
        }

        /*
         * get handle for "vPosition" and "a_texCoord"
         */
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        checkGlError("glGetAttribLocation vPosition");
        if (mPositionHandle == -1) {
            throw new RuntimeException("Could not get attribute location for vPosition");
        }
        mCoordHandle = GLES20.glGetAttribLocation(mProgram, "a_texCoord");
        checkGlError("glGetAttribLocation a_texCoord");
        if (mCoordHandle == -1) {
            throw new RuntimeException("Could not get attribute location for a_texCoord");
        }

        /*
         * get uniform location for y/u/v, we pass data through these uniforms
         */
        mHandleY = GLES20.glGetUniformLocation(mProgram, "tex_y");
        checkGlError("glGetUniformLocation tex_y");
        if (mHandleY == -1) {
            throw new RuntimeException("Could not get uniform location for tex_y");
        }
        mHandleU = GLES20.glGetUniformLocation(mProgram, "tex_u");
        checkGlError("glGetUniformLocation tex_u");
        if (mHandleU == -1) {
            throw new RuntimeException("Could not get uniform location for tex_u");
        }
        mHandleV = GLES20.glGetUniformLocation(mProgram, "tex_v");
        checkGlError("glGetUniformLocation tex_v");
        if (mHandleV == -1) {
            throw new RuntimeException("Could not get uniform location for tex_v");
        }

        mIsProgBuilt = true;
    }

    /**
     * build a set of textures, one for R, one for G, and one for B.
     */
    public void buildTextures(Buffer y, Buffer u, Buffer v, int width, int height) {
        boolean videoSizeChanged = (width != mVideoWidth || height != mVideoHeight);
        if (videoSizeChanged) {
            mVideoWidth = width;
            mVideoHeight = height;
        }

        // building texture for Y data
        if (mTidY < 0 || videoSizeChanged) {
            if (mTidY >= 0) {
                GLES20.glDeleteTextures(1, new int[] {mTidY}, 0);
                checkGlError("glDeleteTextures");
            }
            // GLES20.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT, 1);
            int[] textures = new int[1];
            GLES20.glGenTextures(1, textures, 0);
            checkGlError("glGenTextures");
            mTidY = textures[0];
        }
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTidY);
        checkGlError("glBindTexture");
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE, mVideoWidth, mVideoHeight, 0,
                GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, y);
        checkGlError("glTexImage2D");
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        // building texture for U data
        if (mTidU < 0 || videoSizeChanged) {
            if (mTidU >= 0) {
                GLES20.glDeleteTextures(1, new int[] {mTidU}, 0);
                checkGlError("glDeleteTextures");
            }
            int[] textures = new int[1];
            GLES20.glGenTextures(1, textures, 0);
            checkGlError("glGenTextures");
            mTidU = textures[0];
        }
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTidU);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE, mVideoWidth / 2, mVideoHeight / 2, 0,
                GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, u);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        // building texture for V data
        if (mTidV < 0 || videoSizeChanged) {
            if (mTidV >= 0) {
                GLES20.glDeleteTextures(1, new int[] {mTidV}, 0);
                checkGlError("glDeleteTextures");
            }
            int[] textures = new int[1];
            GLES20.glGenTextures(1, textures, 0);
            checkGlError("glGenTextures");
            mTidV = textures[0];
        }
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTidV);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE, mVideoWidth / 2, mVideoHeight / 2, 0,
                GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, v);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
    }

    /**
     * render the frame
     * the YUV data will be converted to RGB by shader.
     */
    public void drawFrame() {
        GLES20.glUseProgram(mProgram);
        checkGlError("glUseProgram");

        GLES20.glVertexAttribPointer(mPositionHandle, 2, GLES20.GL_FLOAT, false, 8, mVertexBuffer);
        checkGlError("glVertexAttribPointer mPositionHandle");
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        GLES20.glVertexAttribPointer(mCoordHandle, 2, GLES20.GL_FLOAT, false, 8, mCoordBuffer);
        checkGlError("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(mCoordHandle);

        // bind textures
        GLES20.glActiveTexture(mTextureI);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTidY);
        GLES20.glUniform1i(mHandleY, mIndexI);

        GLES20.glActiveTexture(mTextureII);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTidU);
        GLES20.glUniform1i(mHandleU, mIndexII);

        GLES20.glActiveTexture(mTextureIII);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTidV);
        GLES20.glUniform1i(mHandleV, mIndexIII);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        // GLES20.glFinish();
        GLES20.glFlush();

        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mCoordHandle);
    }

    /**
     * create program and load shaders, fragment shader is very important.
     */
    public int createProgram(String vertexSource, String fragmentSource) {
        // create shaders
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        // just check

        int program = GLES20.glCreateProgram();
        if (program != 0) {
            GLES20.glAttachShader(program, vertexShader);
            checkGlError("glAttachShader");
            GLES20.glAttachShader(program, pixelShader);
            checkGlError("glAttachShader");
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }

    /**
     * create shader with given source.
     */
    private int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    /**
     * these two buffers are used for holding vertices, screen vertices and texture vertices.
     */
    void createBuffers(float[] vert) {
        mVertexBuffer = ByteBuffer.allocateDirect(vert.length * 4);
        mVertexBuffer.order(ByteOrder.nativeOrder());
        mVertexBuffer.asFloatBuffer().put(vert);
        mVertexBuffer.position(0);

        if (mCoordBuffer == null) {
            mCoordBuffer = ByteBuffer.allocateDirect(coordVertices.length * 4);
            mCoordBuffer.order(ByteOrder.nativeOrder());
            mCoordBuffer.asFloatBuffer().put(coordVertices);
            mCoordBuffer.position(0);
        }
    }

    private void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            throw new RuntimeException(op + ": glError " + error);
        }
    }

    static float[] squareVertices = { -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, }; // fullscreen

    static float[] squareVertices1 = { -1.0f, 0.0f, 0.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f, }; // left-top

    static float[] squareVertices2 = { 0.0f, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f, }; // right-bottom

    static float[] squareVertices3 = { -1.0f, -1.0f, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, }; // left-bottom

    static float[] squareVertices4 = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, }; // right-top

    private static float[] coordVertices = { 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, }; // whole-texture

    private static final String VERTEX_SHADER = "attribute vec4 vPosition;\n" + "attribute vec2 a_texCoord;\n"
            + "varying vec2 tc;\n" + "void main() {\n" + "gl_Position = vPosition;\n" + "tc = a_texCoord;\n" + "}\n";

    private static final String FRAGMENT_SHADER = "precision mediump float;\n" + "uniform sampler2D tex_y;\n"
            + "uniform sampler2D tex_u;\n" + "uniform sampler2D tex_v;\n" + "varying vec2 tc;\n" + "void main() {\n"
            + "vec4 c = vec4((texture2D(tex_y, tc).r - 16./255.) * 1.164);\n"
            + "vec4 U = vec4(texture2D(tex_u, tc).r - 128./255.);\n"
            + "vec4 V = vec4(texture2D(tex_v, tc).r - 128./255.);\n" + "c += V * vec4(1.596, -0.813, 0, 0);\n"
            + "c += U * vec4(0, -0.392, 2.017, 0);\n" + "c.a = 1.0;\n" + "gl_FragColor = c;\n" + "}\n";

}