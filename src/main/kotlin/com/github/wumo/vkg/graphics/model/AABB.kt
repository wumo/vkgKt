package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.math.common.FuncCommon.abs
import com.github.wumo.vkg.math.matrix.Mat4
import com.github.wumo.vkg.math.vector.Vec3

class AABB(internal val raw: FloatArray = FloatArray(6)) {
  private val minOffset = 0
  private val maxOffset = 3
  
  val min: Vec3
    get() = Vec3(raw[minOffset + 0], raw[minOffset + 1], raw[minOffset + 2])
  val max: Vec3
    get() = Vec3(raw[maxOffset + 0], raw[maxOffset + 1], raw[maxOffset + 2])
  val center: Vec3
    get() = (min + max) / 2f
  val range: Vec3
    get() = abs(max - min)
  val halfRange: Vec3
    get() = abs(max - min) / 2f
  
  fun merge(p: Vec3): AABB {
    val result = copy()
    AABBMergePoint(result.raw, p.raw)
    return result
  }
  
  fun merge(other: AABB): AABB {
    val result = copy()
    AABBMergeAABB(result.raw, other.raw)
    return result
  }
  
  fun applyTransform(matrix: Mat4): AABB {
    val result = copy()
    AABBTransformMatrix(result.raw, matrix.raw)
    return result
  }
  
  fun applyTransform(transform: Transform): AABB {
    val result = copy()
    AABBTransform(result.raw, transform.raw)
    return result
  }
  
  fun copy(): AABB = AABB(raw.copyOf())
  
  override fun toString(): String {
    return "AABB(min=$min, max=$max)"
  }
}