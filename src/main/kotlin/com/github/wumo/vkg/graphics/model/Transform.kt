package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.math.quat.Quat
import com.github.wumo.vkg.math.vector.Vec3
import com.github.wumo.vkg.math.vector.Vec4

class Transform(
  translation: Vec3 = Vec3(0f, 0f, 0f),
  scale: Vec3 = Vec3(1f, 1f, 1f),
  rotation: Quat = Quat(1f, 0f, 0f, 0f),
  val updateFunc: ((Transform)->Unit)? = null
) {
  private val translationOffset = 0
  private val translationSize = translation.rawSize()
  private val scaleOffset = translationOffset + translationSize
  private val scaleSize = scale.rawSize()
  private val rotationOffset = scaleOffset + scaleSize
  private val rotationSize = rotation.rawSize()
  
  internal val raw: FloatArray = FloatArray(translationSize + scaleSize + rotationSize).also {
    translation.raw.copyInto(it, translationOffset)
    scale.raw.copyInto(it, scaleOffset)
    rotation.raw.copyInto(it, rotationOffset)
  }
  
  var translation: Vec3
    get() = Vec3().also {
      raw.copyInto(it.raw, 0, translationOffset, translationOffset + translationSize)
    }
    set(value) {
      value.raw.copyInto(raw, translationOffset)
      updateFunc?.invoke(this)
    }
  var scale: Vec3
    get() = Vec3().also {
      raw.copyInto(it.raw, 0, scaleOffset, scaleOffset + scaleSize)
    }
    set(value) {
      value.raw.copyInto(raw, scaleOffset)
      updateFunc?.invoke(this)
    }
  var rotation: Quat
    get() = Quat().also {
      raw.copyInto(it.raw, 0, rotationOffset, rotationOffset + rotationSize)
    }
    set(value) {
      value.raw.copyInto(raw, rotationOffset)
      updateFunc?.invoke(this)
    }
  
  override fun toString(): String {
    return "Transform(translation=$translation, scale=$scale, rotation=$rotation)"
  }
}