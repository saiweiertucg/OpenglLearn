package com.practice.gang.opengllearn.util

import android.opengl.GLES30
import android.util.Log

/**
 * Created by gang on 2018/7/18.
 */
class Shader {

    private var tag: String = "Shader"
    private var program: Int = 0

    constructor(tag: String) {
        this.tag = tag
    }

    constructor(tag: String, vertexCode: String, fragmentCode: String) {
        this.tag = tag
        this.program = createProgram(vertexCode, fragmentCode)
    }

    fun createProgram(vertexCode: String, fragmentCode: String): Int {
        var vertexShader = createShader(GLES30.GL_VERTEX_SHADER, vertexCode)
        var fragmentShader = createShader(GLES30.GL_FRAGMENT_SHADER, fragmentCode)

        var program = GLES30.glCreateProgram()
        if (program == 0) {
            Log.i(tag, "create program failed")
            return 0
        }

        var linkState = intArrayOf(0)
        GLES30.glAttachShader(program, vertexShader)
        GLES30.glAttachShader(program, fragmentShader)
        GLES30.glLinkProgram(program)
        GLES30.glGetProgramiv(program, GLES30.GL_LINK_STATUS, linkState, 0)
        if (linkState[0] == 0) {
            Log.i(tag, "link program failed, info: " + GLES30.glGetProgramInfoLog(program))
            GLES30.glDeleteProgram(program)
            return 0
        }

        GLES30.glDeleteShader(vertexShader)
        GLES30.glDeleteShader(fragmentShader)

        return program
    }

    fun createShader(type: Int, code: String): Int {
        var shader: Int
        var compile = intArrayOf(0)

        shader = GLES30.glCreateShader(type)
        if (shader == 0) {
            Log.i(tag, "create shader failed, code is: " + code)
            return 0
        }

        GLES30.glShaderSource(shader, code)
        GLES30.glCompileShader(shader)
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compile, 0)

        if (compile[0] == 0) {
            Log.e(tag, "gl compile failed, info: " + GLES30.glGetShaderInfoLog(shader))
            GLES30.glDeleteShader(shader)
            return 0
        }

        return shader
    }

    fun use(): Unit {
        GLES30.glUseProgram(program)
    }

    fun getAttribLocation(attr: String): Int {
        var handle: Int = GLES30.glGetAttribLocation(program, attr)
        if (handle == -1) {
            throw RuntimeException("Could not find attribute location for: " + attr)
        }
        return handle
    }

    fun getUniformLocation(uniform: String): Int {
        var handle: Int = GLES30.glGetUniformLocation(program, uniform)
        if (handle == -1) {
            throw RuntimeException("Could not find uniform location for: " + uniform)
        }
        return handle
    }
}