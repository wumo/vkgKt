package com.github.wumo.vkg.math.vector

class Vec4 constructor(raw: FloatArray, offset: Int = 0) : Vec<Vec4>(raw, 4, offset) {
  constructor(x: Float, y: Float, z: Float, w: Float)
      : this(floatArrayOf(x, y, z, w))

  constructor(s: Float = 0f) : this(s, s, s, s)
  constructor(v: Vec3, w: Float) : this(v.x, v.y, v.z, w)

  override fun newVec() = Vec4()

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
  inline var w: Float
    get() = raw[offset + 3]
    set(value) {
      raw[offset + 3] = value
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
  inline var a: Float
    get() = w
    set(value) {
      w = value
    }
}

