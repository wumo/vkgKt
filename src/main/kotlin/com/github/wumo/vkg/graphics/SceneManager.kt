package com.github.wumo.vkg.graphics

import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.model.*
import com.github.wumo.vkg.graphics.util.notNull
import com.github.wumo.vkg.graphics.util.useNative

class SceneManager(internal val native: VkgNative.CSceneManager) {
  val camera: Camera = Camera(SceneGetCamera(native.notNull()))
  fun newPrimitives(block: PrimitiveBuilder.()->Unit): List<Primitive> {
    val nativeBuilder = NewPrimitiveBuilder()
    val builder = PrimitiveBuilder(nativeBuilder)
    block(builder)
    val primitiveIndices = IntArray(builder.numPrimitives)
    NewPrimitives(native.notNull(), nativeBuilder, primitiveIndices)
    val primitives = List(primitiveIndices.size) {
      Primitive(this, primitiveIndices[it])
    }
    DeletePrimitiveBuilder(nativeBuilder)
    nativeBuilder.deallocate()
    return primitives
  }
  
  fun newMaterial(type: MaterialType = MaterialType.None): Material {
    return Material(this, NewMaterial(native.notNull(), type.value))
  }
  
  fun newTexture(path: String, mipmap: Boolean = true): Texture {
    val nativeTex = path.useNative {
      NewTexture(native.notNull(), it, mipmap)
    }
    return Texture(this, nativeTex)
  }
  
  fun newMesh(primitive: Primitive, material: Material): Mesh {
    return Mesh(this, NewMesh(native.notNull(), primitive.id, material.id))
  }
  
  fun newNode(transform: Transform = Transform()): Node {
    val nativeNode = NewNode(native.notNull(), transform.raw)
    return Node(this, nativeNode)
  }
  
  fun newModel(vararg nodes: Node): Model {
    val nodeIndices = IntArray(nodes.size) {
      nodes[it].id
    }
    val nativeModel = NewModel(native.notNull(), nodeIndices, nodeIndices.size)
    return Model(this, nativeModel)
  }
  
  fun loadModel(path: String, type: MaterialType = MaterialType.BRDF): Model {
    val nativeModel = path.useNative {
      SceneLoadModel(native.notNull(), it, type.value)
    }
    return Model(this, nativeModel)
  }
  
  fun newModelInstance(model: Model, transform: Transform = Transform()): ModelInstance {
    val nativeInstanve = NewModelInstance(native.notNull(), model.id, transform.raw)
    return ModelInstance(this, nativeInstanve)
  }
  
  fun newLight(): Light {
    return Light(this, NewLight(native.notNull()))
  }
}