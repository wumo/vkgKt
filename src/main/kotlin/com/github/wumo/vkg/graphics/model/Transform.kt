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
  private val scaleOffset = 3
  private val rotationOffset = 3 + 3

  internal val raw: FloatArray = FloatArray(3 + 3 + 4).also {
    it[translationOffset + 0] = translation.x
    it[translationOffset + 1] = translation.y
    it[translationOffset + 2] = translation.z

    it[scaleOffset + 0] = scale.x
    it[scaleOffset + 1] = scale.y
    it[scaleOffset + 2] = scale.z

    it[rotationOffset + 0] = rotation.x
    it[rotationOffset + 1] = rotation.y
    it[rotationOffset + 2] = rotation.z
    it[rotationOffset + 3] = rotation.w
  }

  val translation: Vec3
    get() = Vec3(raw[translationOffset + 0], raw[translationOffset + 1], raw[translationOffset + 2])
  val scale: Vec3
    get() = Vec3(raw[scaleOffset + 0], raw[scaleOffset + 1], raw[scaleOffset + 2])
  val rotation: Vec4
    get() = Vec4(raw[rotationOffset + 0], raw[rotationOffset + 1], raw[rotationOffset + 2], raw[rotationOffset + 3])

}