@file:Suppress("NOTHING_TO_INLINE")

package com.github.wumo.vkg.math.common

import com.github.wumo.vkg.math.vector.Vec3
import com.github.wumo.vkg.math.vector.Vec4
import kotlin.math.abs

object FuncCommon {
  inline fun abs(x: Vec3) =
    Vec3(abs(x.x), abs(x.y), abs(x.z))

  inline fun abs(x: Vec4) =
    Vec4(abs(x.x), abs(x.y), abs(x.z), abs(x.w))

  inline fun mix(x: Float, y: Float, a: Float) = x * (1 - a) + y * a
}