package com.github.wumo.vkg.math.matrix

import com.github.wumo.vkg.math.vector.Vec
import com.github.wumo.vkg.math.vector.Vec4

object FuncMatrix {
  fun determinant(m: Mat3): Float {
    return +m[0, 0] * (m[1, 1] * m[2, 2] - m[2, 1] * m[1, 2]) -
        m[1, 0] * (m[0, 1] * m[2, 2] - m[2, 1] * m[0, 2]) +
        m[2, 0] * (m[0, 1] * m[1, 2] - m[1, 1] * m[0, 2])
  }
  
  fun determinant(m: Mat4): Float {
    val SubFactor00 = m[2, 2] * m[3, 3] - m[3, 2] * m[2, 3];
    val SubFactor01 = m[2, 1] * m[3, 3] - m[3, 1] * m[2, 3];
    val SubFactor02 = m[2, 1] * m[3, 2] - m[3, 1] * m[2, 2];
    val SubFactor03 = m[2, 0] * m[3, 3] - m[3, 0] * m[2, 3];
    val SubFactor04 = m[2, 0] * m[3, 2] - m[3, 0] * m[2, 2];
    val SubFactor05 = m[2, 0] * m[3, 1] - m[3, 0] * m[2, 1];
    
    val DetCof = Vec4(
      +(m[1, 1] * SubFactor00 - m[1, 2] * SubFactor01 + m[1, 3] * SubFactor02),
      -(m[1, 0] * SubFactor00 - m[1, 2] * SubFactor03 + m[1, 3] * SubFactor04),
      +(m[1, 0] * SubFactor01 - m[1, 1] * SubFactor03 + m[1, 3] * SubFactor05),
      -(m[1, 0] * SubFactor02 - m[1, 1] * SubFactor04 + m[1, 2] * SubFactor05)
    )
    
    return m[0, 0] * DetCof[0] + m[0, 1] * DetCof[1] +
        m[0, 2] * DetCof[2] + m[0, 3] * DetCof[3];
  }
  
  fun inverse(m: Mat3, out: Mat3 = Mat3()): Mat3 = out.apply {
    val oneOverDet = 1 / determinant(m)
    
    val res00 = +(m[1, 1] * m[2, 2] - m[2, 1] * m[1, 2]) * oneOverDet
    val res10 = -(m[1, 0] * m[2, 2] - m[2, 0] * m[1, 2]) * oneOverDet
    val res20 = +(m[1, 0] * m[2, 1] - m[2, 0] * m[1, 1]) * oneOverDet
    val res01 = -(m[0, 1] * m[2, 2] - m[2, 1] * m[0, 2]) * oneOverDet
    val res11 = +(m[0, 0] * m[2, 2] - m[2, 0] * m[0, 2]) * oneOverDet
    val res21 = -(m[0, 0] * m[2, 1] - m[2, 0] * m[0, 1]) * oneOverDet
    val res02 = +(m[0, 1] * m[1, 2] - m[1, 1] * m[0, 2]) * oneOverDet
    val res12 = -(m[0, 0] * m[1, 2] - m[1, 0] * m[0, 2]) * oneOverDet
    val res22 = +(m[0, 0] * m[1, 1] - m[1, 0] * m[0, 1]) * oneOverDet
    
    out[0, 0] = res00
    out[0, 1] = res01
    out[0, 2] = res02
    
    out[1, 0] = res10
    out[1, 1] = res11
    out[1, 2] = res12
    
    out[2, 0] = res20
    out[2, 1] = res21
    out[2, 2] = res22
  }
  
  fun inverse(m: Mat4, out: Mat4 = Mat4()): Mat4 =
    out.apply {
      val c00 = m[2, 2] * m[3, 3] - m[3, 2] * m[2, 3]
      val c02 = m[1, 2] * m[3, 3] - m[3, 2] * m[1, 3]
      val c03 = m[1, 2] * m[2, 3] - m[2, 2] * m[1, 3]
      
      val c04 = m[2, 1] * m[3, 3] - m[3, 1] * m[2, 3]
      val c06 = m[1, 1] * m[3, 3] - m[3, 1] * m[1, 3]
      val c07 = m[1, 1] * m[2, 3] - m[2, 1] * m[1, 3]
      
      val c08 = m[2, 1] * m[3, 2] - m[3, 1] * m[2, 2]
      val c10 = m[1, 1] * m[3, 2] - m[3, 1] * m[1, 2]
      val c11 = m[1, 1] * m[2, 2] - m[2, 1] * m[1, 2]
      
      val c12 = m[2, 0] * m[3, 3] - m[3, 0] * m[2, 3]
      val c14 = m[1, 0] * m[3, 3] - m[3, 0] * m[1, 3]
      val c15 = m[1, 0] * m[2, 3] - m[2, 0] * m[1, 3]
      
      val c16 = m[2, 0] * m[3, 2] - m[3, 0] * m[2, 2]
      val c18 = m[1, 0] * m[3, 2] - m[3, 0] * m[1, 2]
      val c19 = m[1, 0] * m[2, 2] - m[2, 0] * m[1, 2]
      
      val c20 = m[2, 0] * m[3, 1] - m[3, 0] * m[2, 1]
      val c22 = m[1, 0] * m[3, 1] - m[3, 0] * m[1, 1]
      val c23 = m[1, 0] * m[2, 1] - m[2, 0] * m[1, 1]
      
      val i00 = +(m[1, 1] * c00 - m[1, 2] * c04 + m[1, 3] * c08)
      val i01 = -(m[0, 1] * c00 - m[0, 2] * c04 + m[0, 3] * c08)
      val i02 = +(m[0, 1] * c02 - m[0, 2] * c06 + m[0, 3] * c10)
      val i03 = -(m[0, 1] * c03 - m[0, 2] * c07 + m[0, 3] * c11)
      
      val i10 = -(m[1, 0] * c00 - m[1, 2] * c12 + m[1, 3] * c16)
      val i11 = +(m[0, 0] * c00 - m[0, 2] * c12 + m[0, 3] * c16)
      val i12 = -(m[0, 0] * c02 - m[0, 2] * c14 + m[0, 3] * c18)
      val i13 = +(m[0, 0] * c03 - m[0, 2] * c15 + m[0, 3] * c19)
      
      val i20 = +(m[1, 0] * c04 - m[1, 1] * c12 + m[1, 3] * c20)
      val i21 = -(m[0, 0] * c04 - m[0, 1] * c12 + m[0, 3] * c20)
      val i22 = +(m[0, 0] * c06 - m[0, 1] * c14 + m[0, 3] * c22)
      val i23 = -(m[0, 0] * c07 - m[0, 1] * c15 + m[0, 3] * c23)
      
      val i30 = -(m[1, 0] * c08 - m[1, 1] * c16 + m[1, 2] * c20)
      val i31 = +(m[0, 0] * c08 - m[0, 1] * c16 + m[0, 2] * c20)
      val i32 = -(m[0, 0] * c10 - m[0, 1] * c18 + m[0, 2] * c22)
      val i33 = +(m[0, 0] * c11 - m[0, 1] * c19 + m[0, 2] * c23)
      
      val oneOverDet = 1 / (m[0, 0] * i00 + m[0, 1] * i10 + m[0, 2] * i20 + m[0, 3] * i30)
      
      out[0, 0] = i00 * oneOverDet
      out[0, 1] = i01 * oneOverDet
      out[0, 2] = i02 * oneOverDet
      out[0, 3] = i03 * oneOverDet
      
      out[1, 0] = i10 * oneOverDet
      out[1, 1] = i11 * oneOverDet
      out[1, 2] = i12 * oneOverDet
      out[1, 3] = i13 * oneOverDet
      
      out[2, 0] = i20 * oneOverDet
      out[2, 1] = i21 * oneOverDet
      out[2, 2] = i22 * oneOverDet
      out[2, 3] = i23 * oneOverDet
      
      out[3, 0] = i30 * oneOverDet
      out[3, 1] = i31 * oneOverDet
      out[3, 2] = i32 * oneOverDet
      out[3, 3] = i33 * oneOverDet
    }
  
  fun <M, V> transpose(m: M): M
      where M : Mat<M, V>, V : Vec<V> {
    val result = m.newVec()
    for (a in 0 until m.dim)
      for (b in 0 until m.dim)
        result[a, b] = m[b, a]
    return result
  }
}