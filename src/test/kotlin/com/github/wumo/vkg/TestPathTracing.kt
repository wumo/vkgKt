package com.github.wumo.vkg

import com.github.wumo.vkg.graphics.FeatureConfig
import com.github.wumo.vkg.graphics.PathTracingSceneConfig
import com.github.wumo.vkg.graphics.Renderer
import com.github.wumo.vkg.graphics.WindowConfig

fun main() {
    val windowConfig = WindowConfig()
    val featureConfig = FeatureConfig(true, true)
    val sceneConfig = PathTracingSceneConfig(maxNumLights = 2, sampleCount = 4, maxRecursion = 3)
    val app = Renderer.newPathTracingRenderer(windowConfig, sceneConfig, featureConfig)
    test(app)
}