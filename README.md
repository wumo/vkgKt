# vkg

Graphics Engine on Vulkan written in C/C++ 20

- Deferred Shading
- Cascaded Shadow Map
- Atmospheric Rendering
- Ray tracing on NVIDIA RTX graphics card
- glTF models.

![sample](doc/sample.gif)

## Usage

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
  val reflectiveMat = scene.newMaterial(MaterialType.BRDF)
  reflectiveMat.colorFactor = Vec4(0f, 1f, 0f, 1f)
  reflectiveMat.pbrFactor = Vec4(0f, 0.3f, 0.4f, 0f)
  // primitive + material => mesh
  val mesh = scene.newMesh(primitives[0], reflectiveMat)
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