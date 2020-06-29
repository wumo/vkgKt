package com.github.wumo.vkg.math.vector

import com.github.wumo.vkg.math.common.Vectorizable
import com.github.wumo.vkg.math.matrix.Mat

abstract class Vec<V : Vec<V>>(val raw: FloatArray, val dim: Int) :
  Vectorizable<V> {
  init {
    assert(raw.size == dim) { "vector size not match" }
  }
  
  override fun rawSize() = raw.size
  override fun rawGet(idx: Int) = raw[idx]
  override fun rawSet(idx: Int, v: Float) {
    raw[idx] = v
  }
  
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as Vec<*>
    return raw.contentEquals(other.raw)
  }
  
  override fun hashCode(): Int {
    return raw.contentHashCode()
  }
  
  fun copy(): V {
    val c = newVec()
    raw.copyInto(c.raw)
    return c
  }
  
  operator fun get(idx: Int): Float {
    assert(idx in 0 until dim)
    return raw[idx]
  }
  
  @Suppress("UNCHECKED_CAST")
  operator fun unaryPlus(): V {
    return this as V
  }
  
  operator fun unaryMinus() =
    copy().also {
      for (i in 0..it.raw.lastIndex)
        it.raw[i] = -it.raw[i]
    }
  
  operator fun plusAssign(v: V) {
    for (i in 0..raw.lastIndex)
      raw[i] += v.raw[i]
  }
  
  operator fun plus(v: V) = copy().also { it += v }
  
  operator fun plusAssign(s: Float) {
    for (i in 0..raw.lastIndex)
      raw[i] += s
  }
  
  operator fun plus(s: Float) = copy().also { it += s }
  
  operator fun minusAssign(v: V) {
    for (i in 0..raw.lastIndex)
      raw[i] -= v.raw[i]
  }
  
  operator fun minus(v: V) = copy().also { it -= v }
  
  operator fun minusAssign(s: Float) {
    for (i in 0..raw.lastIndex)
      raw[i] -= s
  }
  
  operator fun minus(s: Float) = copy().also { it -= s }
  
  operator fun timesAssign(v: V) {
    for (i in 0..raw.lastIndex)
      raw[i] *= v.raw[i]
  }
  
  operator fun times(v: V) = copy().also { it *= v }
  
  operator fun timesAssign(s: Float) {
    for (i in 0..raw.lastIndex)
      raw[i] *= s
  }
  
  operator fun times(s: Float) = copy().also { it *= s }
  
  operator fun divAssign(v: V) {
    for (i in 0..raw.lastIndex)
      raw[i] /= v.raw[i]
  }
  
  operator fun div(v: V) = copy().also { it /= v }
  
  operator fun divAssign(s: Float) {
    for (i in 0..raw.lastIndex)
      raw[i] /= s
  }
  
  operator fun div(s: Float) = copy().also { it /= s }
  
  fun divBy(s: Float): V {
    val v = newVec()
    for (i in 0..v.raw.lastIndex)
      v.raw[i] = s / raw[i]
    return v
  }
  
  operator fun <M> times(m: Mat<M, V>): V
      where M : Mat<M, V> {
    val result = newVec()
    for (r in 0 until dim) {
      var sum = 0f
      for (i in 0 until dim)
        sum += raw[i] * m.raw[r * dim + i]
      result.raw[r] = sum
    }
    return result
  }
  
  fun sum() = raw.sum()
  
  override fun toString(): String {
    return "Vec$dim${raw.contentToString()}"
  }
  
}

operator fun <V : Vec<V>> Float.plus(v: V) = v + this
operator fun <V : Vec<V>> Float.minus(v: V) = -v + this
operator fun <V : Vec<V>> Float.times(v: V) = v * this
operator fun <V : Vec<V>> Float.div(v: V) = v.divBy(this)

