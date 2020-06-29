package com.github.wumo.vkg

import com.github.wumo.vkg.graphics.RayTracingSceneConfig
import com.github.wumo.vkg.graphics.Renderer
import com.github.wumo.vkg.graphics.WindowConfig
import com.github.wumo.vkg.graphics.model.MaterialType.*
import com.github.wumo.vkg.graphics.model.Transform
import com.github.wumo.vkg.graphics.util.PanningCamera
import com.github.wumo.vkg.math.common.FuncCommon.abs
import com.github.wumo.vkg.math.quat.QuatTrigonometric.angleAxis
import com.github.wumo.vkg.math.vector.FuncTrigonometric.radians
import com.github.wumo.vkg.math.vector.Vec3
import com.github.wumo.vkg.math.vector.Vec4
import kotlin.math.max

fun test(app: Renderer) {
  val scene = app.scene
  run {
    val light = scene.newLight()
    light.color = Vec3(1f, 1f, 1f)
    var loc = Vec3(1000f, 2000f, 2000f)
    loc = angleAxis(radians(-60f), Vec3(0f, 1f, 0f)) * loc
    light.location = loc
    light.intensity = 10f
  }
  val yellowMat = scene.newMaterial()
  yellowMat.colorFactor = Vec4(1f, 1f, 0f, 1f)
  val redMat = scene.newMaterial()
  redMat.colorFactor = Vec4(1f, 0f, 0f, 1f)
  val greenMat = scene.newMaterial()
  greenMat.colorFactor = Vec4(0f, 1f, 0f, 1f)
  val blueMat = scene.newMaterial()
  blueMat.colorFactor = Vec4(0f, 0f, 1f, 1f)
  val texMat = scene.newMaterial(BRDF)
  val colorTex = scene.newTexture("src/main/cpp/assets/TextureCoordinateTemplate.png")
  texMat.colorTex = colorTex
  texMat.pbrFactor = Vec4(0f, 0.3f, 0.4f, 1f)
  
  run {
    val primitives = scene.newPrimitives {
      axis(Vec3(), 10f, 0.1f, 0.5f, 50)
      newPrimitive()
    }
    val originMesh = scene.newMesh(primitives[0], yellowMat)
    val xMesh = scene.newMesh(primitives[1], redMat)
    val yMesh = scene.newMesh(primitives[2], greenMat)
    val zMesh = scene.newMesh(primitives[3], blueMat)
    val axisNode = scene.newNode()
    axisNode.addMeshes(originMesh, xMesh, yMesh, zMesh)
    val axisModel = scene.newModel(axisNode)
    scene.newModelInstance(axisModel)
  }
  
  run {
    val primitive = scene.newPrimitives {
      rectangle(Vec3(), Vec3(5f, 0f, -5f), Vec3(0f, 10f, 0f))
      newPrimitive()
    }[0]
    val mesh = scene.newMesh(primitive, texMat)
    val node = scene.newNode()
    node.addMeshes(mesh)
    val model = scene.newModel(node)
    scene.newModelInstance(model)
  }
  
  run {
    val primitive = scene.newPrimitives {
      checkerboard(100, 100, Vec3(), Vec3(0f, 0f, 1f), Vec3(1f, 0f, 0f), 4f, 4f)
      newPrimitive()
    }[0]
    val pbrMat = scene.newMaterial(BRDF)
    val gridTex = scene.newTexture("src/main/cpp/assets/grid.png")
    pbrMat.colorTex = gridTex
    pbrMat.pbrFactor = Vec4(0f, 0.3f, 0.4f, 0f)
    val mesh = scene.newMesh(primitive, pbrMat)
    val node = scene.newNode()
    node.addMeshes(mesh)
    val model = scene.newModel(node)
    scene.newModelInstance(model)
  }
  
  run {
    val primitive = scene.newPrimitives {
      box(Vec3(), Vec3(0f, 0f, 1f), Vec3(1f, 0f, 0f), 1f)
      newPrimitive()
    }[0]
    val pbrMat = scene.newMaterial(Reflective)
    pbrMat.colorFactor = Vec4(0f, 1f, 0f, 1f)
    pbrMat.pbrFactor = Vec4(0f, 0.3f, 0.3f, 0f)
    val mesh = scene.newMesh(primitive, pbrMat)
    val node = scene.newNode(Transform(Vec3(10f, 1f, 0f)))
    node.addMeshes(mesh)
    val model = scene.newModel(node)
    scene.newModelInstance(model)
  }
  
  run {
    val primitive = scene.newPrimitives {
      sphere(Vec3(0f, 2f, 10f), 2f)
      newPrimitive()
    }[0]
    val pbrMat = scene.newMaterial(Refractive)
    pbrMat.colorFactor = Vec4(1f, 1f, 1f, 1f)
    pbrMat.pbrFactor = Vec4(0f, 0.3f, 0.3f, 0f)
    val mesh = scene.newMesh(primitive, pbrMat)
    val node = scene.newNode()
    node.addMeshes(mesh)
    val model = scene.newModel(node)
    scene.newModelInstance(model)
  }
  
  run {
    val name = "DamagedHelmet"
    val model = scene.loadModel("src/main/cpp/assets/glTF-models/2.0/" + name + "/glTF/" + name + ".gltf")
    val aabb = model.aabb
    val range = aabb.max - aabb.min
    val scale = 5 / max(max(range.x, range.y), range.z)
    val center = (aabb.min + aabb.max) / 2f
    val halfRange = abs(aabb.max - aabb.min) / 2f
    val t = Transform(
      -center * scale + Vec3(8f, scale * range.y / 2, 8f),
      Vec3(scale, scale, scale)
    )
    scene.newModelInstance(model, t)
  }
  
  scene.camera.location = Vec3(20f, 20f, 20f)
  val panningCamera = PanningCamera(scene.camera)
  val input = app.window.input
  app.loop {
    panningCamera.update(input)
  }
  
  app.close()
}