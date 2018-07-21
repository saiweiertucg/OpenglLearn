package com.practice.gang.opengllearn.renderer.gettingstart

import android.content.Context
import android.opengl.GLES30
import com.practice.gang.opengllearn.renderer.BaseRenderer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer

/**
 * Created by gang on 2018/7/21.
 */
class RectangleRenderer : BaseRenderer {

    private var data: FloatArray = floatArrayOf(
            0.5f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f
    )

    private var indices: IntArray = intArrayOf(
            0, 1, 3, // first triangle // note that start from 0
            1, 2, 3  // second triangle
    )
    private lateinit var indicesBuffer: IntBuffer

    override var vertexDataBuffer: FloatBuffer

    constructor(context: Context) : super(context) {
        vertexDataBuffer = ByteBuffer.allocateDirect(data.size * FLOAT_SIZE).
                order(ByteOrder.nativeOrder()).asFloatBuffer()
        vertexDataBuffer.put(data, 0, data.size).position(0)
        indicesBuffer = ByteBuffer.allocateDirect(indices.size * INT_SIZE).
                order(ByteOrder.nativeOrder()).asIntBuffer()
        indicesBuffer.put(indices, 0, indices.size).position(0)
    }

    override fun createVAO(vao: IntArray) {
        var vbo = intArrayOf(0)
        var ebo = intArrayOf(0)
        GLES30.glGenVertexArrays(1, vao, 0)
        GLES30.glGenBuffers(1, vbo, 0)
        GLES30.glGenBuffers(1, ebo, 0)

        GLES30.glBindVertexArray(vao[0])
        //VBO
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo[0])
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vertexDataBuffer.capacity() * FLOAT_SIZE, vertexDataBuffer, GLES30.GL_STATIC_DRAW)
        //EBO
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, ebo[0])
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer.capacity() * INT_SIZE, indicesBuffer, GLES30.GL_STATIC_DRAW)

        GLES30.glVertexAttribPointer(0, COORDS_PER_VERTEX, GLES30.GL_FLOAT, false, COORDS_PER_VERTEX * FLOAT_SIZE, 0)
        GLES30.glEnableVertexAttribArray(0)

        // note that this is allowed, the call to glVertexAttribPointer registered VBO as the vertex attribute's bound vertex buffer object
        // so afterwards we can safely unbind
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0)

        // remember: do NOT unbind the EBO while a VAO is active as the bound element buffer object IS stored in the VAO; keep the EBO bound.
        //GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0)

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
        GLES30.glDrawElements(GLES30.GL_LINE_LOOP, 6, GLES30.GL_UNSIGNED_INT, 0)
        GLES30.glBindVertexArray(0)
    }

}