package com.github.wumo.vkg.math.vector

class Vec3 private constructor(raw: FloatArray) : Vec<Vec3>(raw, 3) {
  constructor(x: Float , y: Float , z: Float )
      : this(floatArrayOf(x, y, z))
  
  constructor(s: Float = 0f) : this(s, s, s)
  constructor(v: Vec4) : this(v.x, v.y, v.z)
  
  override fun newVec() = Vec3()
  
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