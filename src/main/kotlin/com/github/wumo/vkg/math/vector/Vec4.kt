package com.github.wumo.vkg.math.vector

class Vec4 private constructor(raw: FloatArray) : Vec<Vec4>(raw, 4) {
  constructor(x: Float, y: Float, z: Float, w: Float)
      : this(floatArrayOf(x, y, z, w))
  
  constructor(s: Float = 0f) : this(s, s, s, s)
  constructor(v: Vec3, w: Float) : this(v.x, v.y, v.z, w)
  
  override fun newVec() = Vec4()
  
  inline var x: Float
    get() = raw[0]
    set(value) {
      raw[0] = value
    }
  inline var y: Float
    get() = raw[1]
    set(value) {
      raw[1] = value
    }
  inline var z: Float
    get() = raw[2]
    set(value) {
      raw[2] = value
    }
  inline var w: Float
    get() = raw[3]
    set(value) {
      raw[3] = value
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

