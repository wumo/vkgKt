package com.github.wumo.vkg

import com.github.wumo.vkg.graphics.FeatureConfig
import com.github.wumo.vkg.graphics.Renderer
import com.github.wumo.vkg.graphics.SceneConfig
import com.github.wumo.vkg.graphics.WindowConfig
import com.github.wumo.vkg.graphics.model.*
import com.github.wumo.vkg.graphics.util.PanningCamera
import com.github.wumo.vkg.math.common.FuncCommon.abs
import com.github.wumo.vkg.math.quat.QuatTrigonometric.angleAxis
import com.github.wumo.vkg.math.vector.FuncTrigonometric.radians
import com.github.wumo.vkg.math.vector.Vec3
import com.github.wumo.vkg.math.vector.Vec4
import kotlin.math.*
import kotlin.random.Random

fun main() {
  val app = Renderer.newRenderer(
    WindowConfig(),
    FeatureConfig(numFrames = 2, rayTrace = true)
  )
  val scene = app.addScene(
    SceneConfig(
      maxNumTransforms = 100_0000,
      maxNumPrimitives = 100_000,
      maxNumMeshInstances = 100_0000
    )
  )
  
  val kPi = PI.toFloat()
  val seasonAngle = kPi / 4f
  var sunAngle = 0f
  val angularVelocity = kPi / 20
  val sunDirection = { dt: Float->
    sunAngle += angularVelocity * dt;
    if(sunAngle > 2 * kPi) sunAngle = 0f
    
    -Vec3(
      cos(sunAngle), abs(sin(sunAngle) * sin(seasonAngle)),
      -sin(sunAngle) * cos(seasonAngle)
    )
  }
  
  val sky = scene.atmosphere
  sky.enabled = true
  sky.sunIntensity = 10f
  sky.sunDirection = sunDirection(1f)
  
  val shadowMap = scene.shadowMap
  shadowMap.enabled = true
  shadowMap.numCascades = 4
  shadowMap.zFar = 1e3f
  
  val yellowMat = scene.newMaterial()
  yellowMat.colorFactor = Vec4(1f, 1f, 0f, 1f)
  val redMat = scene.newMaterial()
  redMat.colorFactor = Vec4(1f, 0f, 0f, 1f)
  val greenMat = scene.newMaterial()
  greenMat.colorFactor = Vec4(0f, 1f, 0f, 1f)
  val blueMat = scene.newMaterial()
  blueMat.colorFactor = Vec4(0f, 0f, 1f, 1f)
  val texMat = scene.newMaterial(MaterialType.BRDF)
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
    val pbrMat = scene.newMaterial(MaterialType.BRDF)
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
    val pbrMat = scene.newMaterial(MaterialType.Reflective)
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
    val pbrMat = scene.newMaterial(MaterialType.Refractive)
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
      newPrimitive(PrimitiveTopology.Lines)
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
      newPrimitive(PrimitiveTopology.Lines)
    }
    val transRedMat = scene.newMaterial(MaterialType.Transparent)
    transRedMat.colorFactor = Vec4(1f, 0f, 0f, 0.5f)
    val node = scene.newNode()
    node.addMeshes(scene.newMesh(primitives[0], transRedMat))
    scene.newModelInstance(scene.newModel(node))
    
    val node2 = scene.newNode()
    node2.addMeshes(scene.newMesh(primitives[1], transRedMat))
    scene.newModelInstance(scene.newModel(node2))
  }
  
  val insts = mutableListOf<ModelInstance>()
  run {
    val name = "DamagedHelmet"
    val model = scene.loadModel("src/main/cpp/assets/glTF-models/2.0/$name/glTF/$name.gltf")
    val aabb = model.aabb
    val range = aabb.max - aabb.min
    val scale = 5 / max(max(range.x, range.y), range.z)
    val center = (aabb.min + aabb.max) / 2f
    val t = Transform(
      -center * scale + Vec3(8f, scale * range.y / 2, 8f),
      Vec3(scale, scale, scale)
    )
    scene.newModelInstance(model, t)
    
    val num = 100
    val unit = -5f
    for(a in 0 until num)
      for(b in 0 until num) {
        val _t = Transform(
          -center * scale +
              Vec3(-10f + unit * a, scale * range.y / 2, -10f + unit * b),
          Vec3(scale, scale, scale)
        )
        insts += scene.newModelInstance(model, _t, true)
      }
  }
  
  val parentNode: Node
  val childNode: Node
  
  run { // node graph
    val primitive = scene.newPrimitives {
      box(Vec3(), Vec3(0f, 7f, 0f), Vec3(0f, 1f, 0f), 0.2f, 0.2f)
      newPrimitive()
    }[0]
    
    childNode = scene.newNode(
      Transform(
        Vec3(0f, 7f, 0f), Vec3(1f, 1f, 1f),
        angleAxis(radians(60f), Vec3(1f, 0f, 0f))
      )
    )
    childNode.addMeshes(scene.newMesh(primitive, redMat))
    
    parentNode = scene.newNode(
      Transform(
        Vec3(0f, 0f, 0f), Vec3(1f, 1f, 1f),
        angleAxis(radians(30f), Vec3(1f, 0f, 0f))
      )
    )
    parentNode.addMeshes(scene.newMesh(primitive, yellowMat))
    parentNode.addChildren(childNode)
    scene.newModelInstance(scene.newModel(parentNode))
  }
  
  val dynamicPrim: Primitive
  val positions = floatArrayOf(0f, 0f, 10f, 10f, 0f, 0f, 0f, 10f, 0f)
  val normals = floatArrayOf(0.5774f, 0.5774f, 0.5774f, 0.5774f, 0.5774f, 0.5774f, 0.5774f, 0.5774f, 0.5774f)
  run {
    dynamicPrim = scene.newPrimitives(true) {
      from(
        positions, normals,
        floatArrayOf(0f, 1f, 1f, 1f, 0f, 0f),
        intArrayOf(0, 1, 2)
      )
      newPrimitive(PrimitiveTopology.Triangels)
    }[0]
    val redMat = scene.newMaterial(MaterialType.Transparent)
    redMat.colorFactor = Vec4(1f, 0f, 0f, 0.5f)
    val mesh = scene.newMesh(dynamicPrim, redMat)
    val node = scene.newNode()
    node.addMeshes(mesh)
    val model = scene.newModel(node)
    scene.newModelInstance(model)
  }
  
  val dynamicPrim2: Primitive
  run {
    dynamicPrim2 = scene.newPrimitives(true) {
      box(Vec3(), Vec3(0f, 0f, 1f), Vec3(1f, 0f, 0f), 1f)
      newPrimitive(PrimitiveTopology.Triangels)
    }[0]
    val mesh = scene.newMesh(dynamicPrim2, texMat)
    val node = scene.newNode()
    node.addMeshes(mesh)
    val model = scene.newModel(node)
    scene.newModelInstance(model)
  }
  
  val balls = mutableListOf<ModelInstance>()
  val ballModel: Model
  val boxModel: Model
  run {
    val primitives = scene.newPrimitives {
      sphere(Vec3(), 1f)
      newPrimitive()
      box(Vec3(), Vec3(0f, 0f, 1f), Vec3(1f, 0f, 0f), 1f)
      newPrimitive()
    }
    val mat = scene.newMaterial(MaterialType.None)
    mat.colorFactor = Vec4(1f, 1f, 1f, 0.5f)
    var mesh = scene.newMesh(primitives[0], mat)
    var node = scene.newNode()
    node.addMeshes(mesh)
    ballModel = scene.newModel(node)
    mesh = scene.newMesh(primitives[1], mat)
    node = scene.newNode()
    node.addMeshes(mesh)
    boxModel = scene.newModel(node)
    
    val t = Transform()
    val num = 10
    val unit = 5f
    for(a in 0 until num) {
      for(b in 0 until num) {
        t.translation = Vec3(-10f + unit * a, 10f, -10f + unit * b)
        balls += scene.newModelInstance(ballModel, t)
      }
    }
  }
  
  val camera = scene.camera
  val panningCamera = PanningCamera(camera)
  val input = app.window.input
  
  camera.location = Vec3(22.759f, 8.61548f, 26.1782f)
  camera.zfar = 1e9f
  
  var lastChange = System.currentTimeMillis()
  var totalElapsed = 0.0
  val fpsMeter = app.fpsMeter
  app.loop { frameIdx, elapsedMs->
    app.window.title = "FPS: ${fpsMeter.fps} Frame Time: ${round(fpsMeter.frameTime)} ms"
    
    panningCamera.update(input)
    
    sky.sunDirection = sunDirection((elapsedMs / 1000).toFloat())
    
    for(inst in insts) {
      val t = inst.transform
      t.rotation = angleAxis(radians(elapsedMs.toFloat() * 0.1f), Vec3(0f, 1f, 0f)) * t.rotation
    }
    val now = System.currentTimeMillis()
    if(now - lastChange > 2000) {
      lastChange = now
      for(ball in balls) {
        ball.model = if(Random.nextBoolean()) boxModel else ballModel
        if(Random.nextBoolean())
          ball.visible = !ball.visible
        ball.material = if(Random.nextBoolean()) blueMat else greenMat
      }
    }
    
    val rot = angleAxis(radians(elapsedMs.toFloat() / 10), Vec3(-1f, 0f, 1f))
    
    for(i in 0 until positions.size / 3) {
      val v = Vec3(positions[i * 3], positions[i * 3 + 1], positions[i * 3 + 2])
      val n = Vec3(normals[i * 3], normals[i * 3 + 1], normals[i * 3 + 2])
      val v2 = rot * v
      val n2 = rot * n
      positions[i * 3] = v2.x
      positions[i * 3 + 1] = v2.y
      positions[i * 3 + 2] = v2.z
      
      normals[i * 3] = n2.x
      normals[i * 3 + 1] = n2.y
      normals[i * 3 + 2] = n2.z
    }
    
    dynamicPrim.update(frameIdx, positions, normals, AABB())
    
    dynamicPrim2.update(frameIdx) {
      totalElapsed += elapsedMs / 1000
      val s = 10 * abs(sin(totalElapsed)).toFloat()
      box(Vec3(), Vec3(0f, 0f, s), Vec3(s, 0f, 0f), s)
      newPrimitive(PrimitiveTopology.Triangels)
    }
  }
  
  app.close()
}