package com.github.wumo.vkg.math.quat

import com.github.wumo.vkg.math.quat.QuatCommon.inverse
import com.github.wumo.vkg.math.quat.QuatGeometric.dot
import com.github.wumo.vkg.math.vector.*
import com.github.wumo.vkg.math.vector.FuncGeometric.clamp
import com.github.wumo.vkg.math.vector.FuncGeometric.cross
import com.github.wumo.vkg.math.vector.VectorRelational.epsilonEquals
import kotlin.math.asin
import kotlin.math.atan2

object FuncQuat {
  /** Returns pitch value of euler angles expressed in radians. */
  fun pitch(q: Quat): Float {
    //return T(atan(T(2) * (q.y * q.z + q.w * q.x), q.w * q.w - q.x * q.x - q.y * q.y + q.z * q.z));
    val y = 2 * (q.y * q.z + q.w * q.x)
    val x = q.w * q.w - q.x * q.x - q.y * q.y + q.z * q.z
    
    //avoid atan2(0,0) - handle singularity - Matiis
    return if (epsilonEquals(x, y, 0f, 0f))
      2 * atan2(q.x, q.w)
    else atan2(y, x)
  }
  
  /** Returns roll value of euler angles expressed in radians. */
  fun roll(q: Quat): Float = atan2(
    2 * (q.x * q.y + q.w * q.z),
    q.w * q.w + q.x * q.x - q.y * q.y - q.z * q.z
  )
  
  /** Returns yaw value of euler angles expressed in radians. */
  fun yaw(q: Quat): Float =
    asin(clamp(-2 * (q.x * q.z - q.w * q.y), -1f, 1f))
  
  /**
   * Returns euler angles, pitch as x, yaw as y, roll as z.
   * The result is expressed in radians.
   */
  fun eulerAngles(q: Quat, out: Vec3 = Vec3()): Vec3 =
    out.apply {
      val x_ = pitch(q)
      val y_ = yaw(q)
      val z_ = roll(q)
      x = x_
      y = y_
      z = z_
    }
  
  fun unaryMinus(q: Quat, out: Quat = Quat()): Quat =
    out.apply {
      w = -q.w
      x = -q.x
      y = -q.y
      z = -q.z
    }
  
  fun plus(p: Quat, q: Quat, out: Quat = Quat()): Quat =
    out.apply {
      w = p.w + q.w
      x = p.x + q.x
      y = p.y + q.y
      z = p.z + q.z
    }
  
  fun minus(p: Quat, q: Quat, out: Quat = Quat()): Quat =
    out.apply {
      w = p.w - q.w
      x = p.x - q.x
      y = p.y - q.y
      z = p.z - q.z
    }
  
  fun times(
    pW: Float, pX: Float, pY: Float, pZ: Float,
    qW: Float, qX: Float, qY: Float, qZ: Float, out: Quat = Quat()
  ): Quat = out.apply {
    val _w = pW * qW - pX * qX - pY * qY - pZ * qZ
    val _x = pW * qX + pX * qW + pY * qZ - pZ * qY
    val _y = pW * qY + pY * qW + pZ * qX - pX * qZ
    val _z = pW * qZ + pZ * qW + pX * qY - pY * qX
    w = _w
    x = _x
    y = _y
    z = _z
  }
  
  fun times(
    p: Quat,
    qW: Float, qX: Float, qY: Float, qZ: Float,
    out: Quat = Quat()
  ): Quat = times(p.w, p.x, p.y, p.z, qW, qX, qY, qZ, out)
  
  fun times(
    pW: Float, pX: Float, pY: Float, pZ: Float,
    q: Quat, out: Quat = Quat()
  ): Quat = times(pW, pX, pY, pZ, q.w, q.x, q.y, q.z, out)
  
  fun times(p: Quat, q: Quat, out: Quat = Quat()): Quat =
    times(p.w, p.x, p.y, p.z, q.w, q.x, q.y, q.z, out)
  
  fun times(q: Quat, s: Float, out: Quat = Quat()): Quat =
    out.apply {
      w = q.w * s
      x = q.x * s
      y = q.y * s
      z = q.z * s
    }
  
  fun div(q: Quat, s: Float, out: Quat = Quat()): Quat =
    out.apply {
      w = q.w / s
      x = q.x / s
      y = q.y / s
      z = q.z / s
    }
  
  fun times(qW: Float, qX: Float, qY: Float, qZ: Float, v: Vec3, out: Vec3): Vec3 =
    out.apply {
      cross(qX, qY, qZ, v.x, v.y, v.z) { uvX, uvY, uvZ ->
        cross(qX, qY, qZ, uvX, uvY, uvZ) { uuvX, uuvY, uuvZ ->
          x = v.x + (uvX * qW + uuvX) * 2f
          y = v.y + (uvY * qW + uuvY) * 2f
          z = v.z + (uvZ * qW + uuvZ) * 2f
        }
      }
    }
  
  fun times(q: Quat, v: Vec3, out: Vec3 = Vec3()): Vec3 =
    times(q.w, q.x, q.y, q.z, v, out)
  
  fun times(v: Vec3, q: Quat, out: Vec3 = Vec3()): Vec3 =
    out.apply {
      inverse(q) { iW, iX, iY, iZ ->
        times(iW, iX, iY, iZ, v, out)
      }
    }
  
  fun times(
    qW: Float, qX: Float, qY: Float, qZ: Float,
    v: Vec4, out: Vec4 = Vec4()
  ): Vec4 =
    out.apply {
      w = qW
      x = qX * v.x
      y = qY * v.y
      z = qZ * v.z
    }
  
  fun times(q: Quat, v: Vec4, out: Vec4 = Vec4()): Vec4 =
    out.apply {
      times(q.w, q.x, q.y, q.z, v, out)
    }
  
  fun times(v: Vec4, q: Quat, out: Vec4 = Vec4()): Vec4 =
    out.apply {
      inverse(q) { iW, iX, iY, iZ ->
        times(iW, iX, iY, iZ, v, out)
      }
    }
}