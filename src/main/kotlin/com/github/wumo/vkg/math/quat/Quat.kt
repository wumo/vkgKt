package com.github.wumo.vkg.math.quat

import com.github.wumo.vkg.math.common.Vectorizable
import com.github.wumo.vkg.math.quat.FuncQuat.div
import com.github.wumo.vkg.math.quat.FuncQuat.minus
import com.github.wumo.vkg.math.quat.FuncQuat.plus
import com.github.wumo.vkg.math.quat.FuncQuat.times
import com.github.wumo.vkg.math.quat.FuncQuat.unaryMinus
import com.github.wumo.vkg.math.quat.QuatCommon.inverse
import com.github.wumo.vkg.math.quat.QuatGeometric.normalize
import com.github.wumo.vkg.math.vector.*
import com.github.wumo.vkg.math.vector.FuncGeometric.cross
import com.github.wumo.vkg.math.vector.FuncGeometric.dot
import java.util.*
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.math.cos
import kotlin.math.sin

class Quat constructor(val raw: FloatArray, val offset: Int = 0) : Vectorizable<Quat> {
  companion object {
    /**
     * Create a quaternion from two normalized axis
     *
     * **See Also:** [Beautiful maths simplification: quaternion from two vectors](http://lolengine.net/blog/2013/09/18/beautiful-maths-quaternion-from-vectors)
     * @param u A first normalized axis
     * @param v v A second normalized axis
     */
    operator fun invoke(u: Vec3, v: Vec3): Quat {
      val normUnormV = sqrt(dot(u, u) * dot(v, v))
      var realPart = normUnormV + dot(u, v)
      var tx = 0f
      var ty = 0f
      var tz = 0f
      if (realPart < 1e-6f * normUnormV) {
        // If u and v are exactly opposite, rotate 180 degrees
        // around an arbitrary orthogonal axis. Axis normalisation
        // can happen later, when we normalise the quaternion.
        realPart = 0f
        if (abs(u.x) > abs(u.z)) {
          tx = -u.y
          ty = u.x
          tz = 0f
        } else {
          tx = 0f
          ty = -u.z
          tz = u.y
        }
      } else
        cross(u, v) { x, y, z ->
          tx = x
          ty = y
          tz = z
        }
      return Quat(realPart, tx, ty, tz).also {
        normalize(it, it)
      }
    }

    operator fun invoke(eulerAngles: Vec3): Quat {
      val eX = eulerAngles.x * .5f
      val eY = eulerAngles.y * .5f
      val eZ = eulerAngles.z * .5f
      val cX = cos(eX)
      val cY = cos(eY)
      val cZ = cos(eZ)
      val sX = sin(eX)
      val sY = sin(eY)
      val sZ = sin(eZ)

      return Quat(
          cX * cY * cZ + sX * sY * sZ,
          sX * cY * cZ - cX * sY * sZ,
          cX * sY * cZ + sX * cY * sZ,
          cX * cY * sZ - sX * sY * cZ
      )
    }
  }

  constructor(w: Float = 0f, x: Float = 0f, y: Float = 0f, z: Float = 0f)
      : this(floatArrayOf(x, y, z, w))

  constructor(w: Float, v: Vec3) : this(w, v.x, v.y, v.z)

  val dim = 4

  init {
    assert(offset in 0..raw.lastIndex) { "vector offset is out of bounds [0,raw.size)" }
    assert(offset + dim <= raw.size) { "vector size not match" }
  }

  override fun newVec() = Quat()
  override fun rawSize() = dim
  override fun rawGet(idx: Int) = raw[offset + idx]
  override fun rawSet(idx: Int, v: Float) {
    raw[offset + idx] = v
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as Quat
    return Arrays.equals(raw, offset, offset + dim, other.raw, other.offset, other.offset + other.dim)

  }

  override fun hashCode(): Int {
    var result = 1
    for (i in offset until offset + dim)
      result = 31 * result + java.lang.Float.floatToIntBits(raw[i])
    return result
  }

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

  fun copy() = Quat(raw.copyOfRange(offset, offset + dim))

  fun assign(other: Quat) {
    other.raw.copyInto(raw, offset, other.offset, other.offset + other.dim)
  }

  operator fun unaryPlus() = this
  operator fun unaryMinus() = unaryMinus(this)

  operator fun plus(q: Quat) = plus(this, q)
  operator fun plusAssign(q: Quat) {
    plus(this, q, this)
  }

  operator fun minus(q: Quat) = minus(this, q)
  operator fun minusAssign(q: Quat) {
    minus(this, q, this)
  }

  operator fun times(q: Quat) = times(this, q)
  operator fun timesAssign(q: Quat) {
    times(this, q, this)
  }

  operator fun times(s: Float) = times(this, s)
  operator fun timesAssign(s: Float) {
    times(this, s, this)
  }

  operator fun div(s: Float): Quat = div(this, s)

  operator fun times(v: Vec3): Vec3 = times(this, v)
  operator fun times(v: Vec4): Vec4 = times(this, v)

  override fun toString(): String {
    return buildString {
      append("Quaternion").append(dim).append("[")
      for (i in 0 until dim)
        append(raw[offset + i]).append(",")
      append("]")
    }
  }
}

operator fun Float.times(q: Quat) = q * this
operator fun Vec3.times(q: Quat) = times(this, q)
operator fun Vec4.times(q: Quat) = times(this, q)