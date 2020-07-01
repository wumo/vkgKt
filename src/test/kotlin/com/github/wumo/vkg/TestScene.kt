package com.github.wumo.vkg

import com.github.wumo.vkg.graphics.RayTracingSceneConfig
import com.github.wumo.vkg.graphics.Renderer
import com.github.wumo.vkg.graphics.model.MaterialType.*
import com.github.wumo.vkg.graphics.model.Node
import com.github.wumo.vkg.graphics.model.PrimitiveTopology
import com.github.wumo.vkg.graphics.model.PrimitiveTopology.Lines
import com.github.wumo.vkg.graphics.model.Transform
import com.github.wumo.vkg.graphics.util.PanningCamera
import com.github.wumo.vkg.math.common.FuncCommon.abs
import com.github.wumo.vkg.math.quat.QuatTrigonometric.angleAxis
import com.github.wumo.vkg.math.vector.FuncTrigonometric.radians
import com.github.wumo.vkg.math.vector.Vec3
import com.github.wumo.vkg.math.vector.Vec4
import kotlin.math.*

fun test(app: Renderer) {
  val scene = app.scene
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

  if (app.featureConfig.atmosphere) {
    val sky = app.atmosphere
    sky.init(1.0)
    sky.sunDirection = sunDirection(1f)
    sky.earthCenter = Vec3(0f, (-sky.earthRadius / sky.lengthUnitInMeters - 100).toFloat(), 0f)
  }

//  run {
//    val light = scene.newLight()
//    light.color = Vec3(1f, 1f, 1f)
//    var loc = Vec3(1000f, 2000f, 2000f)
//    loc = angleAxis(radians(-60f), Vec3(0f, 1f, 0f)) * loc
//    light.location = loc
//    light.intensity = 10f
//  }
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

  run { //lines
    val primitives = scene.newPrimitives {
      line(Vec3(0f, 1f, 4f), Vec3(4f, 1f, 0f))
      newPrimitive(Lines)
    }
    val node = scene.newNode()
    node.addMeshes(scene.newMesh(primitives[0], greenMat))
    scene.newModelInstance(scene.newModel(node))
  }

  run { //transparent
    val primitives = scene.newPrimitives {
      rectangle(Vec3(4f, 0f, 4f), Vec3(2f, 0f, -2f), Vec3(0f, 2f, 0f))
      newPrimitive()
      line(Vec3(4f, 0f, 4f), Vec3(4f, 4f, 4f))
      newPrimitive(Lines)
    }
    val transRedMat = scene.newMaterial(Transparent)
    transRedMat.colorFactor = Vec4(1f, 0f, 0f, 0.5f)
    val node = scene.newNode()
    node.addMeshes(scene.newMesh(primitives[0], transRedMat))
    scene.newModelInstance(scene.newModel(node))

    val node2 = scene.newNode()
    node2.addMeshes(scene.newMesh(primitives[1], transRedMat))
    scene.newModelInstance(scene.newModel(node2))
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

    val num = 100
    val unit = -5f
    for (a in 0 until 100)
      for (b in 0 until 100) {
        val _t = Transform(-center * scale +
            Vec3(-10f + unit * a, scale * range.y / 2, -10f + unit * b),
            Vec3(scale, scale, scale))
        scene.newModelInstance(model, _t)
      }
  }

  val parentNode: Node
  val childNode: Node

  run { // node graph
    val primitive = scene.newPrimitives {
      box(Vec3(), Vec3(0f, 7f, 0f), Vec3(0f, 1f, 0f), 0.2f, 0.2f)
      newPrimitive()
    }[0]

    childNode = scene.newNode(Transform(Vec3(0f, 7f, 0f), Vec3(1f, 1f, 1f),
        angleAxis(radians(60f), Vec3(1f, 0f, 0f))))
    childNode.addMeshes(scene.newMesh(primitive, redMat))

    parentNode = scene.newNode(Transform(Vec3(0f, 0f, 0f), Vec3(1f, 1f, 1f),
        angleAxis(radians(30f), Vec3(1f, 0f, 0f))))
    parentNode.addMeshes(scene.newMesh(primitive, yellowMat))
    parentNode.addChildren(childNode)
    scene.newModelInstance(scene.newModel(parentNode))
  }

  scene.camera.location = Vec3(20f, 20f, 20f)
//  scene.camera.zfar = 10000000f
  val panningCamera = PanningCamera(scene.camera)
  val input = app.window.input
  val camera = scene.camera
//  println(camera.direction)
  app.loop { elapsed ->
    panningCamera.update(input)
//    val temp = angleAxis(radians(it.toFloat() / 10f), Vec3(0f, 1f, 0f)) * camera.direction
//    camera.direction = temp
//    println("$temp-> ${camera.direction}, ${camera.location}, ${camera.worldUp}")
    if (app.featureConfig.atmosphere) {
      app.atmosphere.sunDirection = sunDirection((elapsed / 1000).toFloat())
    }
    val t = childNode.transform
    t.rotation = angleAxis(radians(elapsed.toFloat()), Vec3(0f, 1f, 0f)) * t.rotation
    childNode.transform = t
  }

  app.close()
}