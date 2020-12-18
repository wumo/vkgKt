package com.github.wumo.vkg.graphics.util

import com.github.wumo.vkg.graphics.util.Colors.rgbaToInt
import com.github.wumo.vkg.graphics.util.Colors.rgbaToSRGBInt
import kotlin.math.pow

object Colors {
  const val GAMMA = 2.2f
  const val INV_GAMMA = 1f / GAMMA
  private val maxV = UByte.MAX_VALUE.toInt()
  
  fun linearToSRGB(c: Float): Float = c.pow(INV_GAMMA)
  fun sRGBToLinear(c: Float): Float = c.pow(GAMMA)
  
  fun rgbaToInt(r: Float, g: Float, b: Float, a: Float = 1f): Int {
    var c = (r * maxV).toInt() shl 24
    c = c or ((g * maxV).toInt() shl 16)
    c = c or ((b * maxV).toInt() shl 8)
    c = c or ((a * maxV).toInt())
    return c
  }
  
  fun rgbaToSRGBInt(r: Float, g: Float, b: Float, a: Float = 1f): Int {
    var c = (linearToSRGB(r) * maxV).toInt() shl 24
    c = c or ((linearToSRGB(g) * maxV).toInt() shl 16)
    c = c or ((linearToSRGB(b) * maxV).toInt() shl 8)
    c = c or ((linearToSRGB(a) * maxV).toInt())
    return c
  }
}

fun main() {
  val a = rgbaToSRGBInt(1f, 0f, 0f)
  println(a)
}