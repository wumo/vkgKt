package com.github.wumo.vkg.math.quat

import com.github.wumo.vkg.math.common.FuncCommon.mix
import com.github.wumo.vkg.math.common.epsilon
import com.github.wumo.vkg.math.quat.QuatGeometric.dot
import kotlin.math.*

object QuatCommon {
  /**
   * Spherical linear interpolation of two quaternions.
   * The interpolation is oriented and the rotation is performed at constant speed.
   * For short path spherical linear interpolation, use the [slerp] function.
   *
   * @param a A quaternion
   * @param b A quaternion
   * @param interp Interpolation factor. The interpolation is defined beyond the range [0, 1].
   * @see slerp
   */
  fun mix(a: Quat, b: Quat, interp: Float, out: Quat = Quat()): Quat {
    val cosTheta = dot(a, b)
    // Perform a linear interpolation when cosTheta is close to 1 to avoid side effect of sin(angle) becoming a zero denominator
    return if (cosTheta > 1 - epsilon)
      out.apply {
        w = mix(a.w, b.w, interp)
        x = mix(a.x, b.x, interp)
        y = mix(a.y, b.y, interp)
        z = mix(a.z, b.z, interp)
      }
    else {
      // Essential Mathematics, page 467
      val angle = acos(cosTheta)
      val s0 = sin((1 - interp) * angle)
      val s1 = sin(interp * angle)
      val s2 = sin(angle)
      out.apply {
        w = (s0 * a.w + s1 * b.w) / s2
        x = (s0 * a.x + s1 * b.x) / s2
        y = (s0 * a.y + s1 * b.y) / s2
        z = (s0 * a.z + s1 * b.z) / s2
      }
    }
  }
  
  /**
   * Linear interpolation of two quaternions.
   * The interpolation is oriented.
   *
   * @param a A quaternion
   * @param b A quaternion
   * @param interp Interpolation factor. The interpolation is defined in the range [0, 1].
   */
  fun lerp(a: Quat, b: Quat, interp: Float, out: Quat = Quat()): Quat =
    out.apply {
      check(interp in 0f..1f) { "Lerp is only defined in [0, 1]" }
      w = a.w * (1f - interp) + b.w * interp
      x = a.x * (1f - interp) + b.x * interp
      y = a.y * (1f - interp) + b.y * interp
      z = a.z * (1f - interp) + b.z * interp
    }
  
  /**
   * Spherical linear interpolation of two quaternions.
   * The interpolation always take the short path and the rotation is performed at constant speed.
   *
   * @param a A quaternion
   * @param b A quaternion
   * @param interp Interpolation factor. The interpolation is defined beyond the range [0, 1].
   */
  fun slerp(a: Quat, b: Quat, interp: Float, out: Quat = Quat()): Quat {
    var zW = b.w
    var zX = b.x
    var zY = b.y
    var zZ = b.z
    
    var cosTheta = dot(a, b)
    
    // If cosTheta < 0, the interpolation will take the long way around the sphere.
    // To fix this, one quat must be negated.
    if (cosTheta < 0f) {
      zW = -b.w
      zX = -b.x
      zY = -b.y
      zZ = -b.z
      cosTheta = -cosTheta
    }
    
    // Perform a linear interpolation when cosTheta is close to 1 to avoid side effect of sin(angle) becoming a zero denominator
    return if (cosTheta > 1f - epsilon)
      out.apply {
        w = mix(a.w, zW, interp)
        x = mix(a.x, zX, interp)
        y = mix(a.y, zY, interp)
        z = mix(a.z, zZ, interp)
      }
    else {
      // Essential Mathematics, page 467
      val angle = acos(cosTheta)
      val s0 = sin((1f - interp) * angle)
      val s1 = sin(interp * angle)
      val s2 = sin(angle)
      out.apply {
        w = (s0 * a.w + s1 * zW) / s2
        x = (s0 * a.x + s1 * zX) / s2
        y = (s0 * a.y + s1 * zY) / s2
        z = (s0 * a.z + s1 * zZ) / s2
      }
    }
  }
  
  /**
   * Spherical linear interpolation of two quaternions with multiple spins over rotation axis.
   * The interpolation always take the short path when the spin count is positive and long path
   * when count is negative. Rotation is performed at constant speed.
   *
   * @param a A quaternion
   * @param b A quaternion
   * @param interp Interpolation factor. The interpolation is defined beyond the range [0, 1].
   * @param k Additional spin count. If Value is negative interpolation will be on "long" path.
   */
  fun slerp(a: Quat, b: Quat, interp: Float, k: Float, out: Quat = Quat()): Quat {
    var zW = b.w
    var zX = b.x
    var zY = b.y
    var zZ = b.z
    
    var cosTheta = dot(a, b)
    
    // If cosTheta < 0, the interpolation will take the long way around the sphere.
    // To fix this, one quat must be negated.
    if (cosTheta < 0f) {
      zW = -b.w
      zX = -b.x
      zY = -b.y
      zZ = -b.z
      cosTheta = -cosTheta
    }
    
    // Perform a linear interpolation when cosTheta is close to 1 to avoid side effect of sin(angle) becoming a zero denominator
    return if (cosTheta > 1f - epsilon)
      out.apply {
        w = mix(a.w, zW, interp)
        x = mix(a.x, zX, interp)
        y = mix(a.y, zY, interp)
        z = mix(a.z, zZ, interp)
      }
    else {
      // Graphics Gems III, page 96
      val angle = acos(cosTheta)
      val phi = angle + k * PI.toFloat()
      val s0 = sin(angle - interp * phi)
      val s1 = sin(interp * phi)
      val s2 = sin(angle)
      out.apply {
        w = (s0 * a.w + s1 * zW) / s2
        x = (s0 * a.x + s1 * zX) / s2
        y = (s0 * a.y + s1 * zY) / s2
        z = (s0 * a.z + s1 * zZ) / s2
      }
    }
  }
  
  inline fun conjugate(
    q: Quat,
    out: (Float, Float, Float, Float) -> Unit
  ) {
    val w = q.w
    val x = -q.x
    val y = -q.y
    val z = -q.z
    out(w, x, y, z)
  }
  
  /** Returns the q conjugate. */
  fun conjugate(q: Quat, out: Quat = Quat()): Quat =
    out.apply {
      conjugate(q) { w_, x_, y_, z_ ->
        w = w_
        x = x_
        y = y_
        z = z_
      }
    }
  
  /** Returns the q inverse. */
  inline fun inverse(
    q: Quat,
    out: (Float, Float, Float, Float) -> Unit
  ) {
    conjugate(q) { w, x, y, z ->
      val d = dot(q, q)
      out(w / d, x / d, y / d, z / d)
    }
  }
  
  fun inverse(q: Quat, out: Quat = Quat()): Quat =
    out.apply {
      inverse(q) { w_, x_, y_, z_ ->
        w = w_
        x = x_
        y = y_
        z = z_
      }
    }
}