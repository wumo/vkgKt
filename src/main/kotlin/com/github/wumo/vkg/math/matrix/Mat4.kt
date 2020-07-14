package com.github.wumo.vkg.math.matrix

import com.github.wumo.vkg.math.matrix.FuncMatrix.inverse
import com.github.wumo.vkg.math.vector.Vec4

class Mat4 constructor(raw: FloatArray, offset: Int = 0) : Mat<Mat4, Vec4>(raw, 4, offset) {
  constructor(
      x0: Float, y0: Float, z0: Float, w0: Float,
      x1: Float, y1: Float, z1: Float, w1: Float,
      x2: Float, y2: Float, z2: Float, w2: Float,
      x3: Float, y3: Float, z3: Float, w3: Float
  ) : this(floatArrayOf(x0, y0, z0, w0, x1, y1, z1, w1, x2, y2, z2, w2, x3, y3, z3, w3))

  constructor(s: Float = 0f) : this(
      s, 0f, 0f, 0f,
      0f, s, 0f, 0f,
      0f, 0f, s, 0f,
      0f, 0f, 0f, s
  )

  constructor(v0: Vec4, v1: Vec4, v2: Vec4, v3: Vec4) : this(
      v0.x, v0.y, v0.z, v0.w,
      v1.x, v1.y, v1.z, v1.w,
      v2.x, v2.y, v2.z, v2.w,
      v3.x, v3.y, v3.z, v3.w
  )

  override fun newVec() = Mat4()
  override fun newColumn() = Vec4()
  override fun inverse() = inverse(this)
}

