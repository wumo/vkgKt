package com.github.wumo.vkg.graphics

import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.util.bytes
import com.github.wumo.vkg.graphics.util.notNull

class WindowConfig(
    val title: String = "Window",
    val width: Int = 1280, val height: Int = 720,
    val fullscreen: Boolean = false,
    val vsync: Boolean = false,
    val numFrames: Int = 2
)

class FeatureConfig(val atmosphere: Boolean = false,
                    val validationLayer: Boolean = false)

class DeferredSceneConfig(
    val maxNumVertex: Int = 10_000_000,
    val maxNumIndex: Int = 10_000_000,
    val maxNumTransform: Int = 100_000,
    val maxNumMaterial: Int = 10_000,
    val maxNumMeshes: Int = 1_000_000,
    val maxNumLineMeshes: Int = 1_000,
    val maxNumTransparentMeshes: Int = 1_000,
    val maxNumTransparentLineMeshes: Int = 1_000,
    val maxNumTexture: Int = 1_000,
    val maxNumLights: Int = 1,
    val sampleCount: Int = 1
)

class RayTracingSceneConfig(
    val maxNumVertex: Int = 10_000_000,
    val maxNumIndex: Int = 10_000_000,
    val maxNumTransform: Int = 100_000,
    val maxNumMaterial: Int = 10_000,
    val maxNumMeshes: Int = 1_000_000,
    val maxNumLineMeshes: Int = 1_000,
    val maxNumTransparentMeshes: Int = 1_000,
    val maxNumTransparentLineMeshes: Int = 1_000,
    val maxNumTexture: Int = 1_000,
    val maxNumLights: Int = 1,
    val sampleCount: Int = 1,
    val maxRecursion: Int = 3
)

class PathTracingSceneConfig(
    val maxNumVertex: Int = 10_000_000,
    val maxNumIndex: Int = 10_000_000,
    val maxNumTransform: Int = 100_000,
    val maxNumMaterial: Int = 10_000,
    val maxNumMeshes: Int = 1_000_000,
    val maxNumLineMeshes: Int = 1_000,
    val maxNumTransparentMeshes: Int = 1_000,
    val maxNumTransparentLineMeshes: Int = 1_000,
    val maxNumTexture: Int = 1_000,
    val maxNumLights: Int = 1,
    val sampleCount: Int = 1,
    val maxRecursion: Int = 3
)

class Renderer(internal val native: CRenderer, internal val closeFunc: () -> Unit) : AutoCloseable {
  companion object {
    fun newBasicRenderer(windowConfig: WindowConfig, featureConfig: FeatureConfig = FeatureConfig()): Renderer {
      val titleBytes = windowConfig.title.bytes()
      val windowConfig_ = CWindowConfig().also {
        it.title(titleBytes)
        it.width(windowConfig.width)
        it.height(windowConfig.height)
        it.fullscreen(windowConfig.fullscreen)
        it.vsync(windowConfig.vsync)
        it.numFrames(windowConfig.numFrames)
      }
      val featureConfig_ = CFeatureConfig().also {
        it.atmosphere(featureConfig.atmosphere)
        it.validationLayer(featureConfig.validationLayer)
      }
      val native = NewBasicRenderer(windowConfig_, featureConfig_)
      return Renderer(native) {
        DeleteBasicRenderer(native)
      }.apply {
        titleBytes.deallocate()
        windowConfig_.deallocate()
        featureConfig_.deallocate()
      }
    }
    
    fun newDeferredRenderer(windowConfig: WindowConfig, sceneConfig: DeferredSceneConfig, featureConfig: FeatureConfig = FeatureConfig()): Renderer {
      val titleBytes = windowConfig.title.bytes()
      val windowConfig_ = CWindowConfig().also {
        it.title(titleBytes)
        it.width(windowConfig.width)
        it.height(windowConfig.height)
        it.fullscreen(windowConfig.fullscreen)
        it.vsync(windowConfig.vsync)
        it.numFrames(windowConfig.numFrames)
      }
      val featureConfig_ = CFeatureConfig().also {
        it.atmosphere(featureConfig.atmosphere)
        it.validationLayer(featureConfig.validationLayer)
      }
      val sceneConfig_ = CDeferredSceneConfig().also {
        it.maxNumVertex(sceneConfig.maxNumVertex)
        it.maxNumIndex(sceneConfig.maxNumIndex)
        it.maxNumTransform(sceneConfig.maxNumTransform)
        it.maxNumMaterial(sceneConfig.maxNumMaterial)
        it.maxNumMeshes(sceneConfig.maxNumMeshes)
        it.maxNumLineMeshes(sceneConfig.maxNumLineMeshes)
        it.maxNumTransparentMeshes(sceneConfig.maxNumTransparentMeshes)
        it.maxNumTransparentLineMeshes(sceneConfig.maxNumTransparentLineMeshes)
        it.maxNumTexture(sceneConfig.maxNumTexture)
        it.maxNumLights(sceneConfig.maxNumLights)
        it.sampleCount(sceneConfig.sampleCount)
      }
      val native = NewDeferredRenderer(windowConfig_, featureConfig_, sceneConfig_)
      return Renderer(native) {
        DeleteDeferredRenderer(native)
      }.apply {
        titleBytes.deallocate()
        windowConfig_.deallocate()
        featureConfig_.deallocate()
        sceneConfig_.deallocate()
      }
    }
    
    fun newRayTracingRenderer(windowConfig: WindowConfig, sceneConfig: RayTracingSceneConfig, featureConfig: FeatureConfig = FeatureConfig()): Renderer {
      val titleBytes = windowConfig.title.bytes()
      val windowConfig_ = CWindowConfig().also {
        it.title(titleBytes)
        it.width(windowConfig.width)
        it.height(windowConfig.height)
        it.fullscreen(windowConfig.fullscreen)
        it.vsync(windowConfig.vsync)
        it.numFrames(windowConfig.numFrames)
      }
      val featureConfig_ = CFeatureConfig().also {
        it.atmosphere(featureConfig.atmosphere)
        it.validationLayer(featureConfig.validationLayer)
      }
      val sceneConfig_ = CRayTracingSceneConfig().also {
        it.maxNumVertex(sceneConfig.maxNumVertex)
        it.maxNumIndex(sceneConfig.maxNumIndex)
        it.maxNumTransform(sceneConfig.maxNumTransform)
        it.maxNumMaterial(sceneConfig.maxNumMaterial)
        it.maxNumMeshes(sceneConfig.maxNumMeshes)
        it.maxNumLineMeshes(sceneConfig.maxNumLineMeshes)
        it.maxNumTransparentMeshes(sceneConfig.maxNumTransparentMeshes)
        it.maxNumTransparentLineMeshes(sceneConfig.maxNumTransparentLineMeshes)
        it.maxNumTexture(sceneConfig.maxNumTexture)
        it.maxNumLights(sceneConfig.maxNumLights)
        it.sampleCount(sceneConfig.sampleCount)
        it.maxRecursion(sceneConfig.maxRecursion)
      }
      val native = NewRayTracingRenderer(windowConfig_, featureConfig_, sceneConfig_)
      return Renderer(native) {
        DeleteRayTracingRenderer(native)
      }.apply {
        titleBytes.deallocate()
        windowConfig_.deallocate()
        featureConfig_.deallocate()
        sceneConfig_.deallocate()
      }
    }
    
    fun newPathTracingRenderer(windowConfig: WindowConfig, sceneConfig: PathTracingSceneConfig, featureConfig: FeatureConfig = FeatureConfig()): Renderer {
      val titleBytes = windowConfig.title.bytes()
      val windowConfig_ = CWindowConfig().also {
        it.title(titleBytes)
        it.width(windowConfig.width)
        it.height(windowConfig.height)
        it.fullscreen(windowConfig.fullscreen)
        it.vsync(windowConfig.vsync)
        it.numFrames(windowConfig.numFrames)
      }
      val featureConfig_ = CFeatureConfig().also {
        it.atmosphere(featureConfig.atmosphere)
        it.validationLayer(featureConfig.validationLayer)
      }
      val sceneConfig_ = CPathTracingSceneConfig().also {
        it.maxNumVertex(sceneConfig.maxNumVertex)
        it.maxNumIndex(sceneConfig.maxNumIndex)
        it.maxNumTransform(sceneConfig.maxNumTransform)
        it.maxNumMaterial(sceneConfig.maxNumMaterial)
        it.maxNumMeshes(sceneConfig.maxNumMeshes)
        it.maxNumLineMeshes(sceneConfig.maxNumLineMeshes)
        it.maxNumTransparentMeshes(sceneConfig.maxNumTransparentMeshes)
        it.maxNumTransparentLineMeshes(sceneConfig.maxNumTransparentLineMeshes)
        it.maxNumTexture(sceneConfig.maxNumTexture)
        it.maxNumLights(sceneConfig.maxNumLights)
        it.sampleCount(sceneConfig.sampleCount)
        it.maxRecursion(sceneConfig.maxRecursion)
      }
      val native = NewPathTracingRenderer(windowConfig_, featureConfig_, sceneConfig_)
      return Renderer(native) {
        DeletePathTracingRenderer(native)
      }.apply {
        titleBytes.deallocate()
        windowConfig_.deallocate()
        featureConfig_.deallocate()
        sceneConfig_.deallocate()
      }
    }
  }
  
  val scene = SceneManager(GetSceneManager(native.notNull()))
  val atmosphere = Atmosphere(GetAtmosphere(native.notNull()))
  var wireframe: Boolean = RendererGetWireFrame(native.notNull())
    set(value) {
      RendererSetWireFrame(native.notNull(), value)
      field = value
    }
  val window: Window = Window(GetWindow_(native.notNull()))
  fun loop(update: (Double) -> Unit) {
    val updater = object : CallFrameUpdater() {
      override fun update(elapsedDuration: Double) {
        update(elapsedDuration)
      }
    }
    LoopUpdater(native.notNull(), CCallFrameUpdater(updater))
    updater.deallocate()
  }
  
  override fun close() {
    closeFunc()
    native.deallocate()
  }
}
