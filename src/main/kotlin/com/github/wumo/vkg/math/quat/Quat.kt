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
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.math.cos
import kotlin.math.sin

class Quat private constructor(val raw: FloatArray) : Vectorizable<Quat> {
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
  
  override fun newVec() = Quat()
  override fun rawSize() = raw.size
  override fun rawGet(idx: Int) = raw[idx]
  override fun rawSet(idx: Int, v: Float) {
    raw[idx] = v
  }
  
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as Quat
    return raw.contentEquals(other.raw)
  }
  
  override fun hashCode(): Int {
    return raw.contentHashCode()
  }
  
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
  
  fun copy() = Quat(raw.copyOf())
  
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
    return "Quaternion${raw.contentToString()}"
  }
}

operator fun Float.times(q: Quat) = q * this
operator fun Vec3.times(q: Quat) = times(this, q)
operator fun Vec4.times(q: Quat) = times(this, q)