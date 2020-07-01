package com.github.wumo.vkg

import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.util.bytes
import com.github.wumo.vkg.math.vector.Vec3
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

fun main() {
    val title = "Window".bytes()
    val windowConfig = CWindowConfig().also {

        it.title(title)
        it.width(1280)
        it.height(720)
        it.fullscreen(false)
        it.vsync(false)
        it.numFrames(2)
    }

    val sceneConfig = CRayTracingSceneConfig().also {
        it.maxNumVertex(1000_0000)
        it.maxNumIndex(1000_0000)
        it.maxNumTransform(10_0000)
        it.maxNumMaterial(1_0000)
        it.maxNumMeshes(100_0000)
        it.maxNumLineMeshes(1_000)
        it.maxNumTransparentMeshes(1_000)
        it.maxNumTransparentLineMeshes(1_000)
        it.maxNumTexture(1_000)
        it.maxNumLights(1)
        it.sampleCount(4)
        it.maxRecursion(3)
    }
    val featureConfig = CFeatureConfig().also {
        it.atmosphere(true)
        it.validationLayer(true)
    }
    val app = NewRayTracingRenderer(windowConfig, featureConfig, sceneConfig)
    title.deallocate()
    windowConfig.deallocate()
    sceneConfig.deallocate()
    featureConfig.deallocate()
    val scene = RendererGetSceneManager(app)

    run {
        val kPi = PI.toFloat()
        val seasonAngle = kPi / 4f
        var sunAngle = 0f
        val angularVelocity = kPi / 20
        val sunDirection = { dt: Float ->
            sunAngle += angularVelocity * dt;
            if (sunAngle > 2 * kPi) sunAngle = 0f

            -Vec3(
                cos(sunAngle), abs(sin(sunAngle) * sin(seasonAngle)),
                -sin(sunAngle) * cos(seasonAngle)
            )
        }

        val sky = RendererGetAtmosphere(app)
        AtmosphereInit(sky, 1.0, 0.00935 / 2)
        AtmosphereSetSunDirection(sky, sunDirection(1f).raw)
        AtmosphereSetEarthCenter(
            sky,
            Vec3(0f, (-AtmosphereGetEarthRadius(sky) / AtmosphereGetLengthUnitInMeters(sky) - 100).toFloat(), 0f).raw
        )
    }

//    run {
//        val camera = GetCamera(scene)
//        SetCameraDirection(camera, Vec3(1f, 0f, -0.6f).raw)
//    }
    val updater = object : CallFrameUpdater() {
        override fun update(elapsedDuration: Double) {

        }
    }
    RendererLoopUpdater(app, CCallFrameUpdater(updater))
    updater.deallocate()
    DeleteRayTracingRenderer(app)
}