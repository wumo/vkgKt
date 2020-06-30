package com.github.wumo.vkg

import com.github.wumo.vkg.graphics.FeatureConfig
import com.github.wumo.vkg.graphics.RayTracingSceneConfig
import com.github.wumo.vkg.graphics.Renderer
import com.github.wumo.vkg.graphics.WindowConfig

fun main() {
  val windowConfig = WindowConfig()
  val featureConfig = FeatureConfig(true, true)
  val sceneConfig = RayTracingSceneConfig(maxNumLights = 2, sampleCount = 4, maxRecursion = 3)
  val app = Renderer.newRayTracingRenderer(windowConfig, sceneConfig, featureConfig)
  test(app)
}