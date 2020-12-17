package com.github.wumo.vkg.math.matrix

import com.github.wumo.vkg.math.matrix.FuncMatrix.inverse
import com.github.wumo.vkg.math.vector.Vec3

class Mat3 constructor(raw: FloatArray, offset: Int = 0) : Mat<Mat3, Vec3>(raw, 3, offset) {
  constructor(
    x0: Float, y0: Float, z0: Float,
    x1: Float, y1: Float, z1: Float,
    x2: Float, y2: Float, z2: Float
  ) : this(floatArrayOf(x0, y0, z0, x1, y1, z1, x2, y2, z2))

  constructor(s: Float = 0f) : this(
    s, 0f, 0f,
    0f, s, 0f,
    0f, 0f, s
  )

  constructor(v0: Vec3, v1: Vec3, v2: Vec3) : this(
    v0.x, v0.y, v0.z,
    v1.x, v1.y, v1.z,
    v2.x, v2.y, v2.z
  )

  constructor(m: Mat4) : this(
    m.raw[m.offset + 0], m.raw[m.offset + 1], m.raw[m.offset + 2],
    m.raw[m.offset + 4], m.raw[m.offset + 5], m.raw[m.offset + 6],
    m.raw[m.offset + 8], m.raw[m.offset + 9], m.raw[m.offset + 10],
  )

  override fun newVec() = Mat3()
  override fun newColumn() = Vec3()
  override fun inverse() = inverse(this)

}