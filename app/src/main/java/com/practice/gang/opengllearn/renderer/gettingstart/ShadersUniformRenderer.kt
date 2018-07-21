package com.practice.gang.opengllearn.renderer.gettingstart

import android.content.Context
import android.opengl.GLES30
import com.practice.gang.opengllearn.renderer.BaseRenderer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Created by gang on 2018/7/21.
 */
class ShadersUniformRenderer : BaseRenderer {

    override var vertexDataBuffer: FloatBuffer

    private var data: FloatArray = floatArrayOf(
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.0f, 0.5f, 0.0f
    )

    constructor(context: Context) : super(context) {
        vertexDataBuffer = ByteBuffer.allocateDirect(data.size * FLOAT_SIZE)
                .order(ByteOrder.nativeOrder()).asFloatBuffer()
        vertexDataBuffer.put(data, 0, data.size).position(0)
    }

    override fun createVAO(vao: IntArray) {
        var vbo: IntArray = intArrayOf(0)
        GLES30.glGenVertexArrays(1, vao, 0)
        GLES30.glGenBuffers(1, vbo, 0)

        GLES30.glBindVertexArray(vao[0])

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo[0])
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vertexDataBuffer.capacity() * FLOAT_SIZE, vertexDataBuffer, GLES30.GL_STATIC_DRAW)

        GLES30.glVertexAttribPointer(0, COORDS_PER_VERTEX, GLES30.GL_FLOAT, false, COORDS_PER_VERTEX * FLOAT_SIZE, 0)
        GLES30.glEnableVertexAttribArray(0)

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0)

        GLES30.glBindVertexArray(0)
    }

    override fun initVertexShaderCode(): String {
        return "#version 300 es\n" +
                "layout (location = 0) in vec3 vPosition;\n" +
                "layout (location = 1) in vec3 color;\n" +
                "out vec3 ourColor;\n" +
                "void main() {\n" +
                "  gl_Position = vec4(vPosition.x, vPosition.y, vPosition.z, 1.0);\n" +
                "  ourColor = color;\n" +
                "}\n"
    }

    override fun initFragmentShaderCode(): String {
        return "#version 300 es\n" +
                "precision mediump float;\n" +
                "out vec4 fragColor;\n" +
                "uniform vec4 ourColor;\n" +
                "void main() {\n" +
                "  fragColor = ourColor;\n" +
                "}\n"
    }

    private var time: Int = 0

    override fun draw() {
        GLES30.glBindVertexArray(vao[0])
        time %= 360
        var greenValue: Float = (Math.sin(time * Math.PI / 180.0) / 2 + 0.5).toFloat()
        time++
        var handle: Int = mShader.getUniformLocation("ourColor")
        GLES30.glUniform4f(handle, 0.0f, greenValue, 0.0f, 1.0f)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3)
        GLES30.glBindVertexArray(0)
    }
}