package com.github.wumo.vkg.math.vector

class Vec2 constructor(raw: FloatArray, offset: Int = 0) : Vec<Vec2>(raw, 2, offset) {
  constructor(x: Float, y: Float)
      : this(floatArrayOf(x, y))

  constructor(s: Float = 0f) : this(s, s)

  override fun newVec() = Vec2()

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