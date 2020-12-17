package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.math.matrix.Mat4
import com.github.wumo.vkg.math.matrix.MatrixTransform
import com.github.wumo.vkg.math.matrix.MatrixTransform.scale_
import com.github.wumo.vkg.math.quat.FuncQuat
import com.github.wumo.vkg.math.quat.FuncQuat.mat4_cast
import com.github.wumo.vkg.math.quat.FuncQuat.quat_cast
import com.github.wumo.vkg.math.quat.Quat
import com.github.wumo.vkg.math.vector.FuncGeometric.length
import com.github.wumo.vkg.math.vector.Vec3
import com.github.wumo.vkg.math.vector.Vec4

class Transform(
  translation: Vec3 = Vec3(0f, 0f, 0f),
  scale: Vec3 = Vec3(1f, 1f, 1f),
  rotation: Quat = Quat(1f, 0f, 0f, 0f),
  val updateFunc: ((Transform) -> Unit)? = null
) {
  companion object {
    operator fun invoke(t: Transform): Transform {
      return Transform(t.translation, t.scale, t.rotation)
    }

    operator fun invoke(m: Mat4): Transform {
      val translation = Vec3(m[3])
      val scale = Vec3(length(m[0]), length(m[1]), length(m[2]))
      scale_(m, Vec3(1 / scale.x, 1 / scale.y, 1 / scale.z))
      m[3] = Vec4(0f, 0f, 0f, 1f)
      val rotation = quat_cast(m)
      return Transform(translation, scale, rotation)
    }
  }

  private val translationOffset = 0
  private val translationSize = translation.rawSize()
  private val scaleOffset = translationOffset + translationSize
  private val scaleSize = scale.rawSize()
  private val rotationOffset = scaleOffset + scaleSize
  private val rotationSize = rotation.rawSize()

  internal val raw: FloatArray = FloatArray(translationSize + scaleSize + rotationSize).also {
    translation.raw.copyInto(it, translationOffset, translation.offset, translation.offset + translationSize)
    scale.raw.copyInto(it, scaleOffset, scale.offset, scale.offset + scaleSize)
    rotation.raw.copyInto(it, rotationOffset, rotation.offset, rotation.offset + rotationSize)
  }

  var translation: Vec3 = Vec3(raw, translationOffset)
    set(value) {
      field.assign(value)
      updateFunc?.invoke(this)
    }
  var scale: Vec3 = Vec3(raw, scaleOffset)
    set(value) {
      field.assign(value)
      updateFunc?.invoke(this)
    }
  var rotation: Quat = Quat(raw, rotationOffset)
    set(value) {
      field.assign(value)
      updateFunc?.invoke(this)
    }

  fun update(t: Transform) {
    t.raw.copyInto(raw)
    updateFunc?.invoke(this)
  }

  fun update(translation: Vec3, scale: Vec3, rotation: Quat) {
    this.translation.assign(translation)
    this.scale.assign(scale)
    this.rotation.assign(rotation)
    updateFunc?.invoke(this)
  }

  override fun toString(): String {
    return "Transform(translation=$translation, scale=$scale, rotation=$rotation)"
  }

  fun update() {
    updateFunc?.invoke(this)
  }

  fun toMatrix(): Mat4 {
    val m = Mat4(1f)
    scale_(m, scale)
    m.assign(mat4_cast(rotation) * m)
    m[3] = Vec4(translation, 1f)
    return m
  }
}