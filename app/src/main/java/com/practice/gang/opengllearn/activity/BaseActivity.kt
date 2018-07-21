package com.practice.gang.opengllearn.activity

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ConfigurationInfo
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

/**
 * Created by gang on 2018/6/12.
 */
abstract class BaseActivity : AppCompatActivity() {

    protected var tag: String = "BaseActivity"

    private val CONTEXT_CLIENT_VERSION: Int = 3
    lateinit var glSurfaceView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glSurfaceView = getGLSurfaceView()
        tag = this.javaClass.simpleName
        if (detectOpenGLES30()) {
            glSurfaceView.setEGLContextClientVersion(CONTEXT_CLIENT_VERSION)
            glSurfaceView.setRenderer(initRenderer())
            //glSurfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
            setContentView(glSurfaceView)
            var title: String = this.javaClass.simpleName
            setTitle(title.substring(0, title.indexOf("Activity")))
        } else {
            Log.i(tag, "CONTEXT_CLIENT_VERSION is lower")
            finish()
        }
    }

    protected fun getGLSurfaceView(): GLSurfaceView {
        return GLSurfaceView(this)
    }

    protected abstract fun initRenderer(): GLSurfaceView.Renderer

    private fun detectOpenGLES30(): Boolean {
        var am: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var info: ConfigurationInfo = am.deviceConfigurationInfo
        return info.reqGlEsVersion >= 0X30000
    }

    override fun onResume() {
        super.onResume()
        glSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        glSurfaceView.onPause()
    }
}