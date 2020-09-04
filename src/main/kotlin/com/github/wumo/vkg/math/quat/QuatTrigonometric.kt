package com.github.wumo.vkg.math.quat

import com.github.wumo.vkg.math.common.cosOneOverTwo
import com.github.wumo.vkg.math.vector.Vec3
import kotlin.math.*

object QuatTrigonometric {
  /** Returns the quaternion rotation angle. */
  fun angle(q: Quat): Float {
    return if (abs(q.w) > cosOneOverTwo)
      asin(sqrt(q.x * q.x + q.y * q.y + q.z * q.z) * 2)
    else acos(q.w) * 2
  }
  
  fun axis(q: Quat, out: Vec3): Vec3 {
    val tmp1 = 1 - q.w * q.w
    return if (tmp1 <= 0)
      out.apply {
        x = 0f
        y = 0f
        z = 1f
      }
    else
      out.apply {
        val tmp2 = 1 / sqrt(tmp1)
        x = q.x * tmp2
        y = q.y * tmp2
        z = q.z * tmp2
      }
  }
  
  /** Returns the q rotation axis. */
  fun axis(q: Quat): Vec3 = axis(q, Vec3())
  
  fun angleAxis(
    angle: Float,
    axisX: Float, axisY: Float, axisZ: Float,
    out: Quat
  ): Quat =
    out.apply {
      val a = angle * 0.5f
      val s = sin(a)
      w = cos(a)
      x = axisX * s
      y = axisY * s
      z = axisZ * s
    }
  
  fun angleAxis(angle: Float, axisX: Float, axisY: Float, axisZ: Float): Quat =
    angleAxis(angle, axisX, axisY, axisZ, Quat())
  
  /**
   * Build a quaternion from an angle and a normalized axis.
   *
   * @param angle Angle expressed in radians.
   * @param axis Axis of the quaternion, must be normalized.
   */
  fun angleAxis(angle: Float, axis: Vec3): Quat =
    angleAxis(angle, axis.x, axis.y, axis.z, Quat())
}