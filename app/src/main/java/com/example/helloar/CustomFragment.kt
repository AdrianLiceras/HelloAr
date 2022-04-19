package com.example.helloar

import androidx.fragment.app.Fragment
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment

class CustomFragment: ArFragment() {
    override fun getSessionConfiguration(session: Session?): Config {
        val config=Config(session)
        config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
        config.focusMode = Config.FocusMode.AUTO
        session?.configure(config)
        this.arSceneView.setupSession(session)
        return config
    }
}