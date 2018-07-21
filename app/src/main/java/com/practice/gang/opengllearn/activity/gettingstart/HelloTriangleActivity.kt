package com.practice.gang.opengllearn.activity.gettingstart

import android.opengl.GLSurfaceView
import com.practice.gang.opengllearn.activity.BaseActivity
import com.practice.gang.opengllearn.renderer.gettingstart.HelloTriangleRenderer

/**
 * Created by gang on 2018/7/17.
 */
class HelloTriangleActivity : BaseActivity() {
    override fun initRenderer(): GLSurfaceView.Renderer {
        return HelloTriangleRenderer(this)
    }
}