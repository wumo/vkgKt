package com.github.wumo.vkg.math.vector

import com.github.wumo.vkg.math.common.Vectorizable
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan
import kotlin.math.asin
import kotlin.math.acos
import kotlin.math.atan

object FuncTrigonometric {
  fun degrees(randians: Float) = randians * 57.295779513082320876798154814105f
  fun radians(degrees: Float) = degrees * 0.01745329251994329576923690768489f
  
  inline fun <V : Vectorizable<V>> compute(v: V, block: (Float) -> Float): V {
    val result = v.newVec()
    for (i in 0 until result.rawSize())
      result.rawSet(i, block(result.rawGet(i)))
    return result
  }
  
  fun <V : Vectorizable<V>> cos(v: V) = compute(v) { cos(it) }
  fun <V : Vectorizable<V>> sin(v: V) = compute(v) { sin(it) }
  fun <V : Vectorizable<V>> tan(v: V) = compute(v) { tan(it) }
  fun <V : Vectorizable<V>> asin(v: V) = compute(v) { asin(it) }
  fun <V : Vectorizable<V>> acos(v: V) = compute(v) { acos(it) }
  fun <V : Vectorizable<V>> atan(v: V) = compute(v) { atan(it) }
}
