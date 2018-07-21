package com.practice.gang.opengllearn.renderer

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import com.practice.gang.opengllearn.util.Shader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by gang on 2018/7/17.
 */
abstract class BaseRenderer: GLSurfaceView.Renderer {

    protected var tag: String = "BaseRenderer"

    private val data = floatArrayOf(
            -0.5f, -0.5f, 0.0f, // Left
            0.5f, -0.5f, 0.0f, // Right
            0.0f, 0.5f, 0.0f  // Top
    )

    protected open lateinit var vertexDataBuffer: FloatBuffer
    protected val FLOAT_SIZE: Int = 4 //Float.SIZE / Byte.SIZE
    protected val INT_SIZE: Int = 4 //Integer.SIZE / Byte.SIZE

    protected val COORDS_PER_VERTEX: Int = 3
    protected val COORDS_PER_COLOR: Int = 3
    protected val COORDS_PER_TEXTURE: Int = 2

    protected var mWidth: Float = 0.0f
    protected var mHeight: Float = 0.0f

    protected lateinit var mShader: Shader

    constructor(context: Context) {
        this.tag = this.javaClass.simpleName

        vertexDataBuffer = ByteBuffer.allocateDirect(data.size * FLOAT_SIZE)
                .order(ByteOrder.nativeOrder()).asFloatBuffer()
        vertexDataBuffer.put(data, 0, data.size).position(0)
    }

    override fun onDrawFrame(gl: GL10?) {
        prepareBackGround()
        // Use the program object
        mShader.use()

        draw()
    }

    //GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3)
    protected open fun draw(): Unit {
        // need to define how to draw
    }

    protected open fun prepareBackGround() {
        // Clear the color buffer
        GLES30.glClearColor(0.2f, 0.3f, 0.3f, 1.0f)
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT or GLES30.GL_COLOR_BUFFER_BIT)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        mWidth = width.toFloat()
        mHeight = height.toFloat()
        GLES30.glViewport(0, 0, width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        mShader = Shader(tag, initVertexShaderCode(), initFragmentShaderCode())

        createVAO(vao)

        prepareTest()
    }

    protected open fun prepareTest() {
        GLES30.glEnable(GLES30.GL_DEPTH_TEST)
    }

    protected var vao = intArrayOf(0)
    abstract protected fun createVAO(vao: IntArray): Unit

    abstract protected fun initVertexShaderCode(): String

    abstract protected fun initFragmentShaderCode(): String
}