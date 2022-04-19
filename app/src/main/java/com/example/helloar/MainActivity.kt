package com.example.helloar

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloar.databinding.ActivityMainBinding
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import java.util.function.Consumer

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var arFragment: CustomFragment
    private lateinit var anchor: Anchor
    private var isPlaced=false
    private enum class AppAnchorState{
        NONE,
        HOSTING,
        HOSTED
    }
    private var appAnchorState=AppAnchorState.NONE
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        arFragment=supportFragmentManager.findFragmentByTag(binding.fragment.toString()) as CustomFragment


        arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            if (!isPlaced) {
                anchor = arFragment.arSceneView.session?.hostCloudAnchor(hitResult.createAnchor())!!
                appAnchorState = AppAnchorState.HOSTING

                crearModel(anchor)

                isPlaced=true
            }
        }
        arFragment.arSceneView.scene.addOnUpdateListener{frameTime->

            if (appAnchorState != AppAnchorState.HOSTING)
                return@addOnUpdateListener
           var cloudAnchorState: Anchor.CloudAnchorState=anchor.cloudAnchorState
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun crearModel(anchor: Anchor) {


        ModelRenderable
            .builder()
            .setSource(this, Uri.parse(""))
            .build()
            .thenAccept( Consumer { modelRenderable-> placeModel(anchor, modelRenderable) })
    }

    private fun placeModel(anchor: Anchor, modelRendeable: ModelRenderable) {
      val anchorNode=  AnchorNode(anchor)
        anchorNode.renderable=modelRendeable
        arFragment.arSceneView.scene.addChild(anchorNode)
    }

}