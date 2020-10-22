package com.github.wumo.sim.math.vector

import com.github.wumo.sim.math.matrix.FuncMatrix.determinant
import com.github.wumo.sim.math.matrix.FuncMatrix.inverse
import com.github.wumo.sim.math.matrix.FuncMatrix.transpose
import com.github.wumo.sim.math.matrix.Mat3
import com.github.wumo.sim.math.matrix.Mat4
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MatTest {
  //       ┌    5.00,    7.00,    9.00,   10.00 ┐
  // A=    │    2.00,    3.00,    3.00,    8.00 │
  //       │    8.00,   10.00,    2.00,    3.00 │
  //       └    3.00,    3.00,    4.00,    8.00 ┘
  //       ┌    3.00,   10.00,   12.00,   18.00 ┐
  // B=    │   12.00,    1.00,    4.00,    9.00 │
  //       │    9.00,   10.00,   12.00,    2.00 │
  //       └    3.00,   12.00,    4.00,   10.00 ┘
  
  val A = Mat4(
    5f, 2f, 8f, 3f,
    7f, 3f, 10f, 3f,
    9f, 3f, 2f, 4f,
    10f, 8f, 3f, 8f
  )
  val B = Mat4(
    3f, 12f, 9f, 3f,
    10f, 1f, 10f, 12f,
    12f, 4f, 12f, 4f,
    18f, 9f, 2f, 10f
  )
  val C = Mat3(
    10f, 4f, 2f,
    20f, 5f, 3f,
    10f, 6f, 5f
  )
  
  @Test
  fun plus() {
    val actual = A + B
    val expect = Mat4(
      8f, 14f, 17f, 6f,
      17f, 4f, 20f, 15f,
      21f, 7f, 14f, 8f,
      28f, 17f, 5f, 18f
    )
    assertEquals(expect, actual, "C≠A+B")
  }
  
  @Test
  fun minus() {
    val actual = A - B
    val expect = Mat4(
      2f, -10f, -1f, 0f,
      -3f, 2f, 0f, -9f,
      -3f, -1f, -10f, 0f,
      -8f, -1f, 1f, -2f
    )
    assertEquals(expect, actual, "C≠A-B")
  }
  
  @Test
  fun times() {
    val actual = A * B
    val expect = Mat4(
      210f, 93f, 171f, 105f,
      267f, 149f, 146f, 169f,
      236f, 104f, 172f, 128f,
      271f, 149f, 268f, 169f
    )
    assertEquals(expect, actual, "C≠A*B")
  }
  
  @Test
  fun determinant3() {
    val det = determinant(C)
    val expect = -70f
    assertEquals(expect, det, "det is wrong")
  }
  
  @Test
  fun determinant4() {
    val det = determinant(A)
    val expect = -361f
    assertEquals(expect, det, "det is wrong")
  }
  
  @Test
  fun inverse3() {
    val _C = inverse(C)
    val expect = Mat3(1f)
    val actual = C * _C
    assertTrue("inverse is wrong") { expect.epsilonEquals(actual) }
  }
  
  @Test
  fun inverse4() {
    val _A = inverse(A)
    val expect = Mat4(1f)
    val actual = A * _A
    assertTrue("inverse is wrong") { expect.epsilonEquals(actual) }
  }
  
  @Test
  fun transpose() {
    val actual = transpose(C)
    val expect = Mat3(
      10f, 20f, 10f,
      4f, 5f, 6f,
      2f, 3f, 5f
    )
    assertEquals(expect, actual, "transpose is wrong")
  }
}