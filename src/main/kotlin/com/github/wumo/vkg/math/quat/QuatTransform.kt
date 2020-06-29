package com.github.wumo.vkg.math.quat

import com.github.wumo.vkg.math.quat.FuncQuat.times
import com.github.wumo.vkg.math.vector.FuncGeometric.length
import com.github.wumo.vkg.math.vector.Vec3
import kotlin.math.abs
import kotlin.math.sin
import kotlin.math.cos

object QuatTransform {
  fun rotate(
    q: Quat, angle: Float,
    axisX: Float, axisY: Float, axisZ: Float,
    out: Quat = Quat()
  ): Quat {
    var tmpX = axisX
    var tmpY = axisY
    var tmpZ = axisZ
    
    // Axis of rotation must be normalised
    val len = length(axisX, axisY, axisZ)
    if (abs(len - 1f) > 0.001f) {
      val oneOverLen = 1f / len
      tmpX *= oneOverLen
      tmpY *= oneOverLen
      tmpZ *= oneOverLen
    }
    val sin = sin(angle * 0.5f)
    
    val pW = cos(angle * 0.5f)
    val pX = tmpX * sin
    val pY = tmpY * sin
    val pZ = tmpZ * sin
    return times(q, pW, pX, pY, pZ, out)
  }
  
  /**
   * Rotates a quaternion from a vector of 3 components axis and an angle.
   *
   * @param q Source orientation
   * @param angle Angle expressed in radians.
   * @param axis Axis of the rotation
   */
  fun rotate(q: Quat, angle: Float, axis: Vec3, out: Quat = Quat()): Quat =
    rotate(q, angle, axis.x, axis.y, axis.z, out)
}