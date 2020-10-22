package com.github.wumo.vkg.math

import com.github.wumo.vkg.math.vector.FuncGeometric.cross
import com.github.wumo.vkg.math.vector.Vec3
import kotlin.test.Test
import kotlin.test.assertEquals

class VecTest {
  val A = Vec3(4f, 6f, 8f)
  val B = Vec3(3f, 2f, 5f)
  
  @Test
  fun cross() {
    val actual = cross(A, B)
    val expect = Vec3(14f, 4f, -10f)
    assertEquals(expect, actual, "cross is wrong")
  }
}