package com.github.wumo.vkg.graphics

import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.util.bytes
import com.github.wumo.vkg.graphics.util.notNull

class WindowConfig(
    val title: String = "Window",
    val width: Int = 1960, val height: Int = 1080,
    val fullscreen: Boolean = false,
    val vsync: Boolean = false,
    val numFrames: Int = 2,
    val safeSync: Boolean = true
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

class Renderer(internal val native: CRenderer,
               val featureConfig: FeatureConfig,
               internal val closeFunc: () -> Unit
) : AutoCloseable {
  companion object {
    fun newBasicRenderer(windowConfig: WindowConfig = WindowConfig(),
                         featureConfig: FeatureConfig = FeatureConfig()): Renderer {
      val titleBytes = windowConfig.title.bytes()
      val windowConfig_ = CWindowConfig().also {
        it.title(titleBytes)
        it.width(windowConfig.width)
        it.height(windowConfig.height)
        it.fullscreen(windowConfig.fullscreen)
        it.vsync(windowConfig.vsync)
        it.numFrames(windowConfig.numFrames)
        it.safeSync(windowConfig.safeSync)
      }
      val featureConfig_ = CFeatureConfig().also {
        it.atmosphere(featureConfig.atmosphere)
        it.validationLayer(featureConfig.validationLayer)
      }
      val native = NewBasicRenderer(windowConfig_, featureConfig_)
      return Renderer(native, featureConfig) {
        DeleteBasicRenderer(native)
      }.apply {
        titleBytes.deallocate()
        windowConfig_.deallocate()
        featureConfig_.deallocate()
      }
    }

    fun newDeferredRenderer(windowConfig: WindowConfig = WindowConfig(),
                            sceneConfig: DeferredSceneConfig = DeferredSceneConfig(),
                            featureConfig: FeatureConfig = FeatureConfig()): Renderer {
      val titleBytes = windowConfig.title.bytes()
      val windowConfig_ = CWindowConfig().also {
        it.title(titleBytes)
        it.width(windowConfig.width)
        it.height(windowConfig.height)
        it.fullscreen(windowConfig.fullscreen)
        it.vsync(windowConfig.vsync)
        it.numFrames(windowConfig.numFrames)
        it.safeSync(windowConfig.safeSync)
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
      return Renderer(native, featureConfig) {
        DeleteDeferredRenderer(native)
      }.apply {
        titleBytes.deallocate()
        windowConfig_.deallocate()
        featureConfig_.deallocate()
        sceneConfig_.deallocate()
      }
    }

    fun newRayTracingRenderer(windowConfig: WindowConfig = WindowConfig(),
                              sceneConfig: RayTracingSceneConfig = RayTracingSceneConfig(),
                              featureConfig: FeatureConfig = FeatureConfig()): Renderer {
      val titleBytes = windowConfig.title.bytes()
      val windowConfig_ = CWindowConfig().also {
        it.title(titleBytes)
        it.width(windowConfig.width)
        it.height(windowConfig.height)
        it.fullscreen(windowConfig.fullscreen)
        it.vsync(windowConfig.vsync)
        it.numFrames(windowConfig.numFrames)
        it.safeSync(windowConfig.safeSync)
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
      return Renderer(native, featureConfig) {
        DeleteRayTracingRenderer(native)
      }.apply {
        titleBytes.deallocate()
        windowConfig_.deallocate()
        featureConfig_.deallocate()
        sceneConfig_.deallocate()
      }
    }

    fun newPathTracingRenderer(windowConfig: WindowConfig = WindowConfig(),
                               sceneConfig: PathTracingSceneConfig = PathTracingSceneConfig(),
                               featureConfig: FeatureConfig = FeatureConfig()): Renderer {
      val titleBytes = windowConfig.title.bytes()
      val windowConfig_ = CWindowConfig().also {
        it.title(titleBytes)
        it.width(windowConfig.width)
        it.height(windowConfig.height)
        it.fullscreen(windowConfig.fullscreen)
        it.vsync(windowConfig.vsync)
        it.numFrames(windowConfig.numFrames)
        it.safeSync(windowConfig.safeSync)
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
      return Renderer(native, featureConfig) {
        DeletePathTracingRenderer(native)
      }.apply {
        titleBytes.deallocate()
        windowConfig_.deallocate()
        featureConfig_.deallocate()
        sceneConfig_.deallocate()
      }
    }
  }

  val scene = SceneManager(RendererGetSceneManager(native.notNull()))
  val atmosphere = Atmosphere(RendererGetAtmosphere(native.notNull()))
  var wireframe: Boolean = RendererGetWireFrame(native.notNull())
    set(value) {
      RendererSetWireFrame(native.notNull(), value)
      field = value
    }
  val window: Window = Window(RendererGetWindow(native.notNull()))
  fun loop(update: (Double) -> Unit) {
    val updater = object : CallFrameUpdater() {
      override fun update(elapsedDuration: Double) {
        update(elapsedDuration)
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
