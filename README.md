vkgKt

[![Release](https://jitpack.io/v/wumo/vkgKt.svg)](https://jitpack.io/#wumo/vkgKt)

Kotlin binding for [vkg](https://github.com/wumo/vkg)

- Deferred Shading
- Cascaded Shadow Mapping
- Atmospheric Rendering
- Ray tracing on NVIDIA RTX graphics card
- Load glTF models.
- Update vertices/transforms/materials/lights per frame.

![sample](doc/sample.gif)

## Usage

Requirements:
* Graphics driver that supports `Vulkan 1.2.0`
* `RayTracing Feature` requires RTX 20 series graphics card.

### gradle:

* Add `jitpack.io` in your root `build.gradle.kts` at the end of repositories:

```kotlin
repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}
```

* Add the dependency

```kotlin
implementation("com.github.wumo:vkgKt:0.0.6")
```



#### Sample code:

```kotlin
fun main() {
  // window and renderer setting
  val app = Renderer.newRenderer(
      WindowConfig("Sample", 1080, 720),
      FeatureConfig(numFrames = 2, rayTrace = true)
  )
  // scene setting
  val scene = app.addScene(
      SceneConfig(
          maxNumTransforms = 100_0000,
          maxNumPrimitives = 100_000,
          maxNumMeshInstances = 100_0000
      )
  )
  // atmosphere setting
  val sky = scene.atmosphere
  sky.enabled = true
  sky.sunIntensity = 10f
  sky.sunDirection = Vec3(-1f, -0.1f, 0f)
  
  // primitive
  val primitives = scene.newPrimitives {
    sphere(Vec3(), 1f)
    newPrimitive()
  }
  // material
  val mat = scene.newMaterial(MaterialType.BRDF)
  mat.colorFactor = Vec4(0f, 1f, 0f, 1f)
  mat.pbrFactor = Vec4(0f, 0.3f, 0.4f, 0f)
  // primitive + material => mesh
  val mesh = scene.newMesh(primitives[0], mat)
  // mesh => node
  val node = scene.newNode()
  node.addMeshes(mesh)
  // node => model
  val model = scene.newModel(node)
  // model => instance
  val sphere = scene.newModelInstance(model)
  
  // scene camera setting
  val camera = scene.camera
  camera.location = Vec3(-5.610391f, 0.049703f, 16.386591f)
  camera.direction = Vec3(5.610391f, -0.049703f, -16.386591f)
  
  // using builtin panning camera to change view
  val panningCamera = PanningCamera(camera)
  // capture input
  val input = app.window.input
  
  //render loop
  app.loop { frameIdx, elapsedMs ->
    // update camera from input
    panningCamera.update(input)
    
    // apply transform per frame
    val t = sphere.transform.translation
    t.x -= elapsedMs.toFloat() * 0.001f
    sphere.transform.translation = t
  }
}
```



