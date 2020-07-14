package com.github.wumo.vkg.math.vector

import com.github.wumo.vkg.math.common.Vectorizable
import com.github.wumo.vkg.math.matrix.Mat
import java.util.*

abstract class Vec<V : Vec<V>>(val raw: FloatArray, val dim: Int, val offset: Int = 0) :
    Vectorizable<V> {
  init {
    assert(offset in 0..raw.lastIndex) { "vector offset is out of bounds [0,raw.size)" }
    assert(offset + dim <= raw.size) { "vector size not match" }
  }

  override fun rawSize() = dim
  override fun rawGet(idx: Int) = raw[offset + idx]
  override fun rawSet(idx: Int, v: Float) {
    raw[offset + idx] = v
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as Vec<*>
    return Arrays.equals(raw, offset, offset + dim, other.raw, other.offset, other.offset + other.dim)
  }

  override fun hashCode(): Int {
    var result = 1
    for (i in offset until offset + dim)
      result = 31 * result + java.lang.Float.floatToIntBits(raw[i])
    return result
  }

  fun copy(): V {
    val c = newVec()
    raw.copyInto(c.raw, c.offset, offset, offset + dim)
    return c
  }

  operator fun get(idx: Int): Float {
    assert(idx in 0 until dim)
    return raw[offset + idx]
  }

  fun assign(other: Vec<V>) {
    other.raw.copyInto(raw, offset, other.offset, other.offset + other.dim)
  }

  @Suppress("UNCHECKED_CAST")
  operator fun unaryPlus(): V {
    return this as V
  }

  operator fun unaryMinus() =
      copy().also {
        for (i in 0 until dim)
          it.raw[it.offset + i] = -it.raw[it.offset + i]
      }

  operator fun plusAssign(v: V) {
    for (i in 0 until dim)
      raw[offset + i] += v.raw[v.offset + i]
  }

  operator fun plus(v: V) = copy().also { it += v }

  operator fun plusAssign(s: Float) {
    for (i in 0 until dim)
      raw[offset + i] += s
  }

  operator fun plus(s: Float) = copy().also { it += s }

  operator fun minusAssign(v: V) {
    for (i in 0 until dim)
      raw[offset + i] -= v.raw[v.offset + i]
  }

  operator fun minus(v: V) = copy().also { it -= v }

  operator fun minusAssign(s: Float) {
    for (i in 0 until dim)
      raw[offset + i] -= s
  }

  operator fun minus(s: Float) = copy().also { it -= s }

  operator fun timesAssign(v: V) {
    for (i in 0 until dim)
      raw[offset + i] *= v.raw[v.offset + i]
  }

  operator fun times(v: V) = copy().also { it *= v }

  operator fun timesAssign(s: Float) {
    for (i in 0 until dim)
      raw[offset + i] *= s
  }

  operator fun times(s: Float) = copy().also { it *= s }

  operator fun divAssign(v: V) {
    for (i in 0 until dim)
      raw[offset + i] /= v.raw[v.offset + i]
  }

  operator fun div(v: V) = copy().also { it /= v }

  operator fun divAssign(s: Float) {
    for (i in 0 until dim)
      raw[offset + i] /= s
  }

  operator fun div(s: Float) = copy().also { it /= s }

  fun divBy(s: Float): V {
    val v = newVec()
    for (i in 0 until dim)
      v.raw[v.offset + i] = s / raw[offset + i]
    return v
  }

  operator fun <M> times(m: Mat<M, V>): V
      where M : Mat<M, V> {
    val result = newVec()
    for (r in 0 until dim) {
      var sum = 0f
      for (i in 0 until dim)
        sum += raw[offset + i] * m.raw[m.offset + r * dim + i]
      result.raw[result.offset + r] = sum
    }
    return result
  }

  fun sum(): Float {
    var sum = 0f
    for (i in 0 until dim)
      sum += raw[offset + i]
    return sum
  }

  override fun toString(): String {
    return buildString {
      append("Vec").append(dim).append("[")
      for (i in 0 until dim)
        append(raw[offset + i]).append(",")
      append("]")
    }
  }
}

operator fun <V : Vec<V>> Float.plus(v: V) = v + this
operator fun <V : Vec<V>> Float.minus(v: V) = -v + this
operator fun <V : Vec<V>> Float.times(v: V) = v * this
operator fun <V : Vec<V>> Float.div(v: V) = v.divBy(this)

