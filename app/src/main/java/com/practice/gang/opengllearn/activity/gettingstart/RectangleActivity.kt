package com.practice.gang.opengllearn.activity.gettingstart

import android.opengl.GLSurfaceView
import com.practice.gang.opengllearn.activity.BaseActivity
import com.practice.gang.opengllearn.renderer.gettingstart.RectangleRenderer

/**
 * Created by gang on 2018/7/21.
 */
class RectangleActivity: BaseActivity() {
    override fun initRenderer(): GLSurfaceView.Renderer {
        return RectangleRenderer(this)
    }
}