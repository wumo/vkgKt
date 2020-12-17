package com.github.wumo.vkg.math.quat

import com.github.wumo.vkg.math.common.epsilon
import kotlin.math.sqrt

object QuatGeometric {
  /** Returns dot product of q1 and q2, i.e., q1[0] * q2[0] + q1[1] * q2[1] + ... */
  fun dot(a: Quat, b: Quat): Float =
    a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w

  /** Returns the norm of a quaternions */
  fun length(q: Quat) = sqrt(dot(q, q))

  /** Returns the normalized quaternion. */
  fun normalize(q: Quat, out: Quat = Quat()): Quat = out.apply {
    val len = length(q)
    if (len < epsilon) {
      w = 1f
      x = 0f
      y = 0f
      z = 0f
    } else {
      val oneOverLen = 1 / len
      w = q.w * oneOverLen
      x = q.x * oneOverLen
      y = q.y * oneOverLen
      z = q.z * oneOverLen
    }
  }

  /** Compute a cross product. */
  fun cross(q1: Quat, q2: Quat, out: Quat = Quat()): Quat =
    out.apply {
      val _w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z
      val _x = q1.w * q2.x + q1.x * q2.w + q1.y * q2.z - q1.z * q2.y
      val _y = q1.w * q2.y + q1.y * q2.w + q1.z * q2.x - q1.x * q2.z
      val _z = q1.w * q2.z + q1.z * q2.w + q1.x * q2.y - q1.y * q2.x
      w = _w
      x = _x
      y = _y
      z = _z
    }
}