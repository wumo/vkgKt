package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.math.vector.Vec3

class AABB(internal val raw: FloatArray = FloatArray(6)) {
  private val minOffset = 0
  private val maxOffset = 3
  
  val min: Vec3
    get() = Vec3(raw[minOffset + 0], raw[minOffset + 1], raw[minOffset + 2])
  val max: Vec3
    get() = Vec3(raw[maxOffset + 0], raw[maxOffset + 1], raw[maxOffset + 2])
}