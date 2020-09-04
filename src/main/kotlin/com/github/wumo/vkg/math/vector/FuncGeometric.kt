package com.github.wumo.vkg.math.vector

import kotlin.math.acos
import kotlin.math.sqrt

object FuncGeometric {
  fun length(x: Float, y: Float): Float = sqrt(x * x + y * y)
  fun length(x: Float, y: Float, z: Float): Float =
      sqrt(x * x + y * y + z * z)

  fun length(x: Float, y: Float, z: Float, w: Float): Float =
      sqrt(x * x + y * y + z * z + w * w)

  fun <V : Vec<V>> length(v: V) = sqrt(dot(v, v))
  fun <V : Vec<V>> distance(p0: V, p1: V) = length(p1 - p0)

  fun <V : Vec<V>> dot(x: V, y: V): Float {
    var sum = 0f
    for (i in 0 until x.rawSize())
      sum += x.rawGet(i) * y.rawGet(i)
    return sum
  }

  fun <V : Vec<V>> normalize(v: V) = v / length(v)

  inline fun cross(
      aX: Float, aY: Float, aZ: Float,
      bX: Float, bY: Float, bZ: Float,
      out: (Float, Float, Float) -> Unit
  ) {
    val x = aY * bZ - bY * aZ
    val y = aZ * bX - bZ * aX
    val z = aX * bY - bX * aY
    out(x, y, z)
  }

  inline fun cross(a: Vec3, b: Vec3, out: (Float, Float, Float) -> Unit): Unit =
      cross(a.x, a.y, a.z, b.x, b.y, b.z, out)

  fun cross(a: Vec3, b: Vec3, out: Vec3): Vec3 =
      out.also {
        cross(a, b) { x, y, z ->
          it.x = x
          it.y = y
          it.z = z
        }
      }

  fun cross(a: Vec3, b: Vec3): Vec3 = cross(a, b, Vec3())

  fun <V : Vec<V>> reflect(I: V, N: V) = I - N * dot(N, I) * 2f

  fun <V : Vec<V>> refract(I: V, N: V, eta: Float): V {
    val dotValue = dot(N, I)
    val k = 1 - eta * eta - dotValue * dotValue
    return if (k >= 0) eta * I - (eta * dotValue + sqrt(k)) * N else I.newVec()
  }

  fun clamp(x: Float, min: Float, max: Float) = x.coerceIn(min, max)

  fun <V : Vec<V>> angle(x: V, y: V) = acos(clamp(dot(x, y), -1f, 1f))
}