package com.github.wumo.vkg.math.matrix

import com.github.wumo.vkg.math.vector.Vec
import com.github.wumo.vkg.math.common.Vectorizable
import com.github.wumo.vkg.math.common.epsilon
import kotlin.math.abs

abstract class Mat<M, V>(val raw: FloatArray, val dim: Int) :
  Vectorizable<M>
    where M : Mat<M, V>, V : Vec<V> {
  init {
    assert(raw.size == dim * dim) { "Matrix should be squared" }
  }
  
  override fun rawSize() = raw.size
  override fun rawGet(idx: Int) = raw[idx]
  override fun rawSet(idx: Int, v: Float) {
    raw[idx] = v
  }
  
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as Mat<*, *>
    return raw.contentEquals(other.raw)
  }
  
  override fun hashCode(): Int {
    return raw.contentHashCode()
  }
  
  fun epsilonEquals(m: M): Boolean {
    for (i in 0..raw.lastIndex)
      if (abs(raw[i] - m.raw[i]) > epsilon)
        return false
    return true
  }
  
  abstract fun newColumn(): V
  
  operator fun get(a: Int, b: Int): Float {
    assert(a in 0 until dim && b in 0 until dim)
    return raw[a * dim + b]
  }
  
  operator fun set(a: Int, b: Int, v: Float) {
    assert(a in 0 until dim && b in 0 until dim)
    raw[a * dim + b] = v
  }
  
  operator fun get(c: Int): V {
    assert(c in 0 until dim)
    val column = newColumn()
    raw.copyInto(column.raw, 0, c * 4, c * 4 + 4)
    return column
  }
  
  fun copy(): M {
    val c = newVec()
    raw.copyInto(c.raw)
    return c
  }
  
  @Suppress("UNCHECKED_CAST")
  operator fun unaryPlus(): M {
    return this as M
  }
  
  operator fun unaryMinus() =
    copy().also {
      for (i in 0..it.raw.lastIndex)
        it.raw[i] = -it.raw[i]
    }
  
  operator fun plusAssign(s: Float) {
    for (i in 0..raw.lastIndex)
      raw[i] += s
  }
  
  operator fun plus(s: Float) = copy().also { it += s }
  
  operator fun plusAssign(m: M) {
    for (i in 0..raw.lastIndex)
      raw[i] += m.raw[i]
  }
  
  operator fun plus(m: M) = copy().also { it += m }
  
  operator fun minusAssign(s: Float) {
    for (i in 0..raw.lastIndex)
      raw[i] -= s
  }
  
  operator fun minus(s: Float) = copy().also { it -= s }
  
  operator fun minusAssign(m: M) {
    for (i in 0..raw.lastIndex)
      raw[i] -= m.raw[i]
  }
  
  operator fun minus(m: M) = copy().also { it -= m }
  
  operator fun timesAssign(s: Float) {
    for (i in 0..raw.lastIndex)
      raw[i] *= s
  }
  
  operator fun times(s: Float) = copy().also { it *= s }
  
  operator fun timesAssign(m: M) {
    (this * m).raw.copyInto(raw)
  }
  
  operator fun times(m: M): M {
    val result = copy()
    for (a in 0 until dim)
      for (b in 0 until dim) {
        var sum = 0f
        for (i in 0 until dim)
          sum += raw[i * dim + a] * m.raw[b * dim + i]
        result.raw[b * dim + a] = sum
      }
    return result
  }
  
  operator fun divAssign(s: Float) {
    for (i in 0..raw.lastIndex)
      raw[i] /= s
  }
  
  operator fun div(s: Float) = copy().also { it /= s }
  
  operator fun divAssign(m: M) {
    this *= m.inverse()
  }
  
  operator fun div(m: M) = copy().also { it /= m }
  
  operator fun times(v: V): V {
    val result = newColumn()
    for (r in 0 until dim) {
      var sum = 0f
      for (i in 0 until dim)
        sum += raw[i * dim + r] * v.raw[i]
      result.raw[r] = sum
    }
    return result
  }
  
  operator fun div(v: V) = inverse() * v
  
  abstract fun inverse(): M
  
  override fun toString() = buildString {
    val head = "Mat${dim}x$dim"
    append(head)
    val tab = CharArray(head.length) { ' ' }
    for (a in 0 until dim) {
      if (a > 0) append(tab)
      val beginChar = when {
        dim == 1 -> '['
        a == 0 -> '┌'
        a == dim - 1 -> '└'
        else -> '│'
      }
      append(beginChar)
      for (b in 0 until dim) {
        val v = get(b, a)
        append("%8.2f".format(v))
        append(if (b < dim - 1) ',' else ' ')
      }
      val endChar = when {
        dim == 1 -> ']'
        a == 0 -> '┐'
        a == dim - 1 -> '┘'
        else -> '│'
      }
      append(endChar)
      appendln()
    }
  }
}

operator fun <M, V> Float.plus(m: M): M
    where M : Mat<M, V>, V : Vec<V> = m + this

operator fun <M, V> Float.minus(m: M): M
    where M : Mat<M, V>, V : Vec<V> = -m + this

operator fun <M, V> Float.times(m: M): M
    where M : Mat<M, V>, V : Vec<V> = m * this