package com.github.wumo.vkg.graphics

import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.util.FPSMeter
import com.github.wumo.vkg.graphics.util.bytes
import com.github.wumo.vkg.graphics.util.notNull

class WindowConfig(
  val title: String = "Window",
  val width: Int = 1960, val height: Int = 1080,
)

class FeatureConfig(
  val fullscreen: Boolean = false,
  val vsync: Boolean = false,
  val numFrames: Int = 1,
  val rayTrace: Boolean = false,
)

class SceneConfig(
  val offsetX: Int = 0,
  val offsetY: Int = 0,
  val extentW: Int = 0,
  val extentH: Int = 0,
  val layer: Int = 0,
  /**max number of vertices*/
  val maxNumVertices: Int = 1000_0000,
  /**max number of indices*/
  val maxNumIndices: Int = 1000_0000,
  /**max number of node and instance transforms*/
  val maxNumTransforms: Int = 10_0000,
  /**max number of materials*/
  val maxNumMaterials: Int = 1_0000,
  /**max number of primitives*/
  val maxNumPrimitives: Int = 100_0000,
  /**max number of mesh instances*/
  val maxNumMeshInstances: Int = 100_0000,
  /**max number of texture including 2d and cube map.*/
  val maxNumTexture: Int = 1_000,
  /**max number of lights*/
  val maxNumLights: Int = 1,
)

class Renderer(
  internal val native: CRenderer,
  val featureConfig: FeatureConfig,
  internal val closeFunc: ()->Unit
): AutoCloseable {
  companion object {
    
    fun newRenderer(
      windowConfig: WindowConfig = WindowConfig(),
      featureConfig: FeatureConfig = FeatureConfig()
    ): Renderer {
      val titleBytes = windowConfig.title.bytes()
      val windowConfig_ = CWindowConfig().also {
        it.title(titleBytes)
        it.width(windowConfig.width)
        it.height(windowConfig.height)
      }
      val featureConfig_ = CFeatureConfig().also {
        it.fullscreen(featureConfig.fullscreen)
        it.vsync(featureConfig.vsync)
        it.numFrames(featureConfig.numFrames)
        it.rayTrace(featureConfig.rayTrace)
      }
      val native = NewRenderer(windowConfig_, featureConfig_)
      return Renderer(native, featureConfig) {
        DeleteRenderer(native)
      }.apply {
        titleBytes.deallocate()
        windowConfig_.deallocate()
        featureConfig_.deallocate()
      }
    }
  }
  
  fun addScene(sceneConfig: SceneConfig = SceneConfig(), name: String = "Scene"): Scene {
    val sceneConfig_ = CSceneConfig().also {
      it.offsetX(sceneConfig.offsetX)
      it.offsetY(sceneConfig.offsetY)
      it.extentW(sceneConfig.extentW)
      it.extentH(sceneConfig.extentH)
      it.layer(sceneConfig.layer)
      it.maxNumVertices(sceneConfig.maxNumVertices)
      it.maxNumIndices(sceneConfig.maxNumIndices)
      it.maxNumTransforms(sceneConfig.maxNumTransforms)
      it.maxNumMaterials(sceneConfig.maxNumMaterials)
      it.maxNumPrimitives(sceneConfig.maxNumPrimitives)
      it.maxNumMeshInstances(sceneConfig.maxNumMeshInstances)
      it.maxNumTexture(sceneConfig.maxNumTexture)
      it.maxNumLights(sceneConfig.maxNumLights)
    }
    val bytes = name.toByteArray()
    val nativeScene = RendererAddScene(native.notNull(), sceneConfig_, bytes, bytes.size)
    return Scene(nativeScene)
  }
  
  val window: Window = Window(RendererGetWindow(native.notNull()))
  
  val fpsMeter: FPSMeter = FPSMeter(RenderGetFPSMeter(native.notNull()))
  
  fun loop(update: (frameIdx: Int, elapsedMs: Double)->Unit) {
    val updater = object: CallFrameUpdater() {
      override fun update(frameIdx: Int, elapsedDuration: Double) {
        update(frameIdx, elapsedDuration)
      }
    }
    RendererLoopUpdater(native.notNull(), CCallFrameUpdater(updater))
    updater.deallocate()
  }
  
  override fun close() {
    closeFunc()
    native.deallocate()
  }
}
