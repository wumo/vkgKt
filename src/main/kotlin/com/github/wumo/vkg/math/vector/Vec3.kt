package com.github.wumo.vkg.math.vector

class Vec3 constructor(raw: FloatArray, offset: Int = 0) : Vec<Vec3>(raw, 3, offset) {
  constructor(x: Float, y: Float, z: Float)
      : this(floatArrayOf(x, y, z))

  constructor(s: Float = 0f) : this(s, s, s)
  constructor(v: Vec4) : this(v.x, v.y, v.z)

  override fun newVec() = Vec3()

  inline var x: Float
    get() = raw[offset + 0]
    set(value) {
      raw[offset + 0] = value
    }
  inline var y: Float
    get() = raw[offset + 1]
    set(value) {
      raw[offset + 1] = value
    }
  inline var z: Float
    get() = raw[offset + 2]
    set(value) {
      raw[offset + 2] = value
    }
  inline var r: Float
    get() = x
    set(value) {
      x = value
    }
  inline var g: Float
    get() = y
    set(value) {
      y = value
    }
  inline var b: Float
    get() = z
    set(value) {
      z = value
    }
}