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
class HelloTriangleRenderer : BaseRenderer {

    override var vertexDataBuffer: FloatBuffer

    val data: FloatArray = floatArrayOf(
            -0.5f, -0.5f, 0.0f, // Left
            0.5f, -0.5f, 0.0f, // Right
            0.0f, 0.5f, 0.0f  // Top
    )

    constructor(context: Context) : super(context) {
        vertexDataBuffer = ByteBuffer.allocateDirect(data.size * FLOAT_SIZE)
                .order(ByteOrder.nativeOrder()).asFloatBuffer()
        vertexDataBuffer.put(data, 0, data.size).position(0)
    }

    override fun createVAO(vao: IntArray) {
        var vbo = intArrayOf(0)
        GLES30.glGenVertexArrays(1, vao, 0)
        GLES30.glGenBuffers(1, vbo, 0)
        // Bind the vertex Array Object first, then bind and set vertex buffer(s) and attribute pointer(s)
        GLES30.glBindVertexArray(vao[0])

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo[0])
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vertexDataBuffer.capacity() * FLOAT_SIZE, vertexDataBuffer, GLES30.GL_STATIC_DRAW)

        GLES30.glVertexAttribPointer(0, COORDS_PER_VERTEX, GLES30.GL_FLOAT, false, COORDS_PER_VERTEX * FLOAT_SIZE, 0)
        GLES30.glEnableVertexAttribArray(0)
        // Note that this is allowed, the call to glVertexAttribPointer registered VBO as the currently bound vertex buffer object
        // so afterwards we can safely unbind
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0)

        // Unbind VAO (it's always a good thing to unbind any buffer/array to prevent strange bugs)
        GLES30.glBindVertexArray(0)
    }

    override fun initVertexShaderCode(): String {
        return "#version 300 es\n" +
                "in vec3 vPosition;\n" +
                "void main() {\n" +
                "  gl_Position = vec4(vPosition.x, vPosition.y, vPosition.z, 1.0);\n" +
                "}\n"
    }

    override fun initFragmentShaderCode(): String {
        return "#version 300 es\n" +
                "precision mediump float;\n" +
                "out vec4 fragColor;\n" +
                "void main() {\n" +
                "  fragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);\n" +
                "}\n"
    }

    override fun draw() {
        GLES30.glBindVertexArray(vao[0])
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3)
        GLES30.glBindVertexArray(0)
    }

}