package com.github.wumo.vkg.math.matrix

import com.github.wumo.vkg.math.vector.Vec
import com.github.wumo.vkg.math.common.Vectorizable
import com.github.wumo.vkg.math.common.epsilon
import java.util.*
import kotlin.math.abs
import kotlin.math.min

abstract class Mat<M, V>(val raw: FloatArray, val dim: Int, val offset: Int = 0) :
  Vectorizable<M>
    where M : Mat<M, V>, V : Vec<V> {
  init {
    assert(offset in 0..raw.lastIndex) { "vector offset is out of bounds [0,raw.size)" }
    assert(offset + dim * dim <= raw.size) { "Matrix should be squared" }
  }

  override fun rawSize() = dim * dim
  override fun rawGet(idx: Int) = raw[offset + idx]
  override fun rawSet(idx: Int, v: Float) {
    raw[offset + idx] = v
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as Mat<*, *>
    return Arrays.equals(
      raw, offset, offset + dim * dim,
      other.raw, other.offset, other.offset + other.dim * other.dim
    )
  }

  override fun hashCode(): Int {
    var result = 1
    for (i in 0 until dim * dim)
      result = 31 * result + java.lang.Float.floatToIntBits(raw[offset + i])
    return result
  }

  fun epsilonEquals(m: M): Boolean {
    for (i in 0 until dim * dim)
      if (abs(raw[offset + i] - m.raw[m.offset + i]) > epsilon)
        return false
    return true
  }

  abstract fun newColumn(): V

  operator fun get(a: Int, b: Int): Float {
    assert(a in 0 until dim && b in 0 until dim)
    return raw[offset + a * dim + b]
  }

  operator fun set(a: Int, b: Int, v: Float) {
    assert(a in 0 until dim && b in 0 until dim)
    raw[offset + a * dim + b] = v
  }

  operator fun <Vv> set(c: Int, v: Vv) where Vv : Vec<Vv> {
    assert(c in 0 until dim)
    v.raw.copyInto(raw, offset + c * 4, v.offset, v.offset + min(dim, v.dim))
  }

  operator fun get(c: Int): V {
    assert(c in 0 until dim)
    val column = newColumn()
    raw.copyInto(column.raw, column.offset, offset + c * 4, offset + c * 4 + 4)
    return column
  }

  fun copy(): M {
    val c = newVec()
    raw.copyInto(c.raw, c.offset, offset, offset + dim * dim)
    return c
  }

  fun assign(other: Mat<M, V>) {
    other.raw.copyInto(raw, offset, other.offset, other.offset + other.dim * other.dim)
  }

  @Suppress("UNCHECKED_CAST")
  operator fun unaryPlus(): M {
    return this as M
  }

  operator fun unaryMinus() =
    copy().also {
      for (i in 0 until dim * dim)
        it.raw[it.offset + i] = -it.raw[it.offset + i]
    }

  operator fun plusAssign(s: Float) {
    for (i in 0 until dim * dim)
      raw[offset + i] += s
  }

  operator fun plus(s: Float) = copy().also { it += s }

  operator fun plusAssign(m: M) {
    for (i in 0 until dim * dim)
      raw[offset + i] += m.raw[offset + i]
  }

  operator fun plus(m: M) = copy().also { it += m }

  operator fun minusAssign(s: Float) {
    for (i in 0 until dim * dim)
      raw[offset + i] -= s
  }

  operator fun minus(s: Float) = copy().also { it -= s }

  operator fun minusAssign(m: M) {
    for (i in 0 until dim * dim)
      raw[offset + i] -= m.raw[offset + i]
  }

  operator fun minus(m: M) = copy().also { it -= m }

  operator fun timesAssign(s: Float) {
    for (i in 0 until dim * dim)
      raw[offset + i] *= s
  }

  operator fun times(s: Float) = copy().also { it *= s }

  operator fun timesAssign(m: M) {
    val result = this * m
    result.raw.copyInto(raw, offset, result.offset, result.offset + result.dim * result.dim)
  }

  operator fun times(m: M): M {
    val result = copy()
    for (a in 0 until dim)
      for (b in 0 until dim) {
        var sum = 0f
        for (i in 0 until dim)
          sum += raw[offset + i * dim + a] * m.raw[m.offset + b * dim + i]
        result.raw[result.offset + b * dim + a] = sum
      }
    return result
  }

  operator fun divAssign(s: Float) {
    for (i in 0 until dim * dim)
      raw[offset + i] /= s
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
        sum += raw[offset + i * dim + r] * v.raw[v.offset + i]
      result.raw[result.offset + r] = sum
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
      appendLine()
    }
  }
}

operator fun <M, V> Float.plus(m: M): M
    where M : Mat<M, V>, V : Vec<V> = m + this

operator fun <M, V> Float.minus(m: M): M
    where M : Mat<M, V>, V : Vec<V> = -m + this

operator fun <M, V> Float.times(m: M): M
    where M : Mat<M, V>, V : Vec<V> = m * this