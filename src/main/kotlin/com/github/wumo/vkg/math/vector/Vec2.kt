package com.github.wumo.vkg.math.vector

class Vec2 private constructor(raw: FloatArray) : Vec<Vec2>(raw, 2) {
  constructor(x: Float, y: Float)
      : this(floatArrayOf(x, y))
  
  constructor(s: Float = 0f) : this(s, s)
  
  override fun newVec() = Vec2()
  
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
}