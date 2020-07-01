package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.math.quat.Quat
import com.github.wumo.vkg.math.vector.Vec3
import com.github.wumo.vkg.math.vector.Vec4

class Transform(
    translation: Vec3 = Vec3(0f, 0f, 0f),
    scale: Vec3 = Vec3(1f, 1f, 1f),
    rotation: Quat = Quat(1f, 0f, 0f, 0f)
) {
  private val translationOffset = 0
  private val translationSize = 0
  private val scaleOffset = 3
  private val scaleSize = 3
  private val rotationOffset = 3 + 3
  private val rotationSize = 4

  internal val raw: FloatArray = FloatArray(3 + 3 + 4).also {
    translation.raw.copyInto(it, translationOffset)
    scale.raw.copyInto(it, scaleOffset)
    rotation.raw.copyInto(it, rotationOffset)
  }

  var translation: Vec3 = Vec3()
    get() = Vec3().also {
      raw.copyInto(it.raw, 0, translationOffset, translationOffset + translationSize)
    }
    set(value) {
      value.raw.copyInto(raw, translationOffset)
      field = value
    }
  var scale: Vec3 = Vec3()
    get() = Vec3().also {
      raw.copyInto(it.raw, 0, scaleOffset, scaleOffset + scaleSize)
    }
    set(value) {
      value.raw.copyInto(raw, scaleOffset)
      field = value
    }
  var rotation: Quat = Quat()
    get() = Quat().also {
      raw.copyInto(it.raw, 0, rotationOffset, rotationOffset + rotationSize)
    }
    set(value) {
      value.raw.copyInto(raw, rotationOffset)
      field = value
    }

  override fun toString(): String {
    return "Transform(translation=$translation, scale=$scale, rotation=$rotation)"
  }

}