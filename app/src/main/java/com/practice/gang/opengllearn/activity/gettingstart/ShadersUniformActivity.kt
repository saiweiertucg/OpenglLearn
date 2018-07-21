package com.practice.gang.opengllearn.activity.gettingstart

import android.opengl.GLSurfaceView
import com.practice.gang.opengllearn.activity.BaseActivity
import com.practice.gang.opengllearn.renderer.gettingstart.ShadersUniformRenderer

/**
 * Created by gang on 2018/7/21.
 */
class ShadersUniformActivity : BaseActivity() {
    override fun initRenderer(): GLSurfaceView.Renderer {
        return ShadersUniformRenderer(this)
    }
}