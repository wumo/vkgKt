package com.github.wumo.vkg.graphics

import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.model.*
import com.github.wumo.vkg.graphics.util.notNull
import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.IntPointer

class Scene(internal val native: VkgNative.CScene) {
  val camera: Camera = Camera(SceneGetCamera(native.notNull()))
  val atmosphere: Atmosphere = Atmosphere(SceneGetAtmosphere(native.notNull()))
  val shadowMap: ShadowMap = ShadowMap(SceneGetShadowmap(native.notNull()))

  fun newPrimitive(
    positions: FloatArray,
    normals: FloatArray,
    uvs: FloatArray,
    indices: IntArray,
    aabb: AABB,
    topology: PrimitiveTopology = PrimitiveTopology.Triangels,
    perFrame: Boolean = false
  ): Primitive {
    val prim = SceneNewPrimitive(
      native.notNull(), positions, 0, positions.size / 3,
      normals, 0, normals.size / 3, uvs, 0, uvs.size / 2, indices, indices.size, aabb.raw,
      topology.value, perFrame
    )
    return Primitive(this, prim)
  }

  fun newPrimitives(perFrame: Boolean = false, block: PrimitiveBuilder.() -> Unit): List<Primitive> {
    val nativeBuilder = NewPrimitiveBuilder()
    val builder = PrimitiveBuilder(nativeBuilder)
    block(builder)
    val primitiveIndices = IntArray(builder.numPrimitives)
    SceneNewPrimitives(native.notNull(), nativeBuilder, perFrame, primitiveIndices)
    val primitives = List(primitiveIndices.size) {
      Primitive(this, primitiveIndices[it])
    }
    DeletePrimitiveBuilder(nativeBuilder)
    nativeBuilder.deallocate()
    return primitives
  }

  fun newMaterial(type: MaterialType = MaterialType.None, perFrame: Boolean = false)
      : Material {
    return Material(this, SceneNewMaterial(native.notNull(), type.value, perFrame))
  }

  fun newTexture(path: String, mipmap: Boolean = true): Texture {
    val bytes = path.toByteArray()
    val nativeTex = SceneNewTexture(native.notNull(), bytes, bytes.size, mipmap)
    return Texture(this, nativeTex)
  }

  fun newTexture(bytes: ByteArray, mipmap: Boolean = true): Texture {
    val bytePtr = BytePointer(*bytes)
    val nativeTex = SceneNewTextureFromMemory(native.notNull(), bytePtr, bytes.size, mipmap)
    bytePtr.deallocate()
    return Texture(this, nativeTex)
  }

  fun newTexture(
    bytes: IntArray,
    width: Int, height: Int,
    format: TextureFormat = TextureFormat.R8G8B8A8Unorm,
    mipmap: Boolean = true
  ): Texture {
    check(bytes.size == width * height) { "only support R8G8B8A8 format" }
    val intPtr = IntPointer(*bytes)
    val bytePtr = BytePointer(intPtr)
    bytePtr.capacity(intPtr.capacity() * intPtr.sizeof())
    val nativeTex =
      SceneNewTextureFromBytes(
        native.notNull(), bytePtr, bytePtr.capacity().toInt(),
        width, height, format.value, mipmap
      )
    intPtr.deallocate()
    return Texture(this, nativeTex)
  }

  fun newTexture(
    bytes: ByteArray,
    width: Int, height: Int,
    format: TextureFormat = TextureFormat.R8G8B8A8Unorm,
    mipmap: Boolean = true
  ): Texture {
    check(bytes.size == width * height * 4) { "only support R8G8B8A8 format" }
    val bytePtr = BytePointer(*bytes)
    val nativeTex = SceneNewTextureFromBytes(
      native.notNull(), bytePtr, bytes.size,
      width, height, format.value, mipmap
    )
    bytePtr.deallocate()
    return Texture(this, nativeTex)
  }

  fun newMesh(primitive: Primitive, material: Material): Mesh {
    return Mesh(this, SceneNewMesh(native.notNull(), primitive.id, material.id))
  }

  fun newNode(transform: Transform = Transform()): Node {
    val nativeNode = SceneNewNode(native.notNull(), transform.raw)
    return Node(this, nativeNode)
  }

  fun newModel(vararg nodes: Node): Model {
    val nodeIndices = IntArray(nodes.size) {
      nodes[it].id
    }
    val nativeModel = SceneNewModel(native.notNull(), nodeIndices, nodeIndices.size)
    return Model(this, nativeModel)
  }

  fun loadModel(path: String, type: MaterialType = MaterialType.BRDF): Model {
    val bytes = path.toByteArray()
    val nativeModel = SceneLoadModel(native.notNull(), bytes, bytes.size, type.value)
    return Model(this, nativeModel)
  }

  fun loadModel(bytes: ByteArray, type: MaterialType = MaterialType.BRDF): Model {
    val bytePtr = BytePointer(*bytes)
    val nativeModel = SceneLoadModelFromBytes(native.notNull(), bytePtr, bytes.size, type.value)
    bytePtr.deallocate()
    return Model(this, nativeModel)
  }

  fun newModelInstance(
    model: Model, transform: Transform = Transform(), perFrame: Boolean = false
  ): ModelInstance {
    val nativeInstanve = SceneNewModelInstance(native.notNull(), model.id, transform.raw, perFrame)
    return ModelInstance(this, nativeInstanve)
  }

  fun newLight(perFrame: Boolean = false): Light {
    return Light(this, SceneNewLight(native.notNull(), perFrame))
  }
}