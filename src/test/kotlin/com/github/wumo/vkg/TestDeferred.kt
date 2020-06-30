package com.github.wumo.vkg

import com.github.wumo.vkg.graphics.DeferredSceneConfig
import com.github.wumo.vkg.graphics.FeatureConfig
import com.github.wumo.vkg.graphics.Renderer
import com.github.wumo.vkg.graphics.WindowConfig

fun main() {
    val windowConfig = WindowConfig()
    val featureConfig = FeatureConfig(true, true)
    val sceneConfig = DeferredSceneConfig(maxNumLights = 2, sampleCount = 4)
    val app = Renderer.newDeferredRenderer(windowConfig, sceneConfig, featureConfig)
    test(app)
}