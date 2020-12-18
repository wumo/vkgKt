package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.Scene
import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.model.PrimitiveTopology.Companion.fromValue
import com.github.wumo.vkg.graphics.util.Range
import com.github.wumo.vkg.graphics.util.notNull

enum class PrimitiveTopology(val value: Long) {
  Triangels(CPrimitiveTriangles),
  Lines(CPrimitiveLines),
  Procedural(CPrimitiveProcedural),
  Patches(CPrimitivePatches);
  
  companion object {
    fun fromValue(value: Long): PrimitiveTopology {
      return when(value) {
        CPrimitiveTriangles -> Triangels
        CPrimitiveLines -> Lines
        CPrimitiveProcedural -> Procedural
        CPrimitivePatches -> Patches
        else                 -> error("Invalid value")
      }
    }
  }
}

class Primitive(val scene: Scene, val id: Int) {
  
  val count: Int = PrimitiveGetCount(scene.native.notNull(), id)
  
  val topology: PrimitiveTopology = fromValue(PrimitiveGetTopology(scene.native.notNull(), id))
  
  fun update(
    idx: Int, positions: FloatArray,
    normals: FloatArray, aabb: AABB
  ) {
    PrimitiveUpdate(
      scene.native.notNull(), id, idx, positions, 0, positions.size / 3,
      normals, 0, normals.size / 3, aabb.raw
    )
  }
  
  fun update(idx: Int, block: PrimitiveBuilder.()->Unit) {
    val nativeBuilder = NewPrimitiveBuilder()
    val builder = PrimitiveBuilder(nativeBuilder)
    block(builder)
    PrimitiveUpdateFromBuilder(scene.native.notNull(), id, idx, nativeBuilder)
    DeletePrimitiveBuilder(nativeBuilder)
    nativeBuilder.deallocate()
  }
}