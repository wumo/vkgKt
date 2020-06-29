package com.github.wumo.vkg.math.matrix

import com.github.wumo.vkg.math.matrix.Mat4
import kotlin.math.tan

object MatrixClipSpace {
  /**
   *  Creates a matrix for a right handed, symetric perspective-view frustum.
   * The near and far clip planes correspond to z normalized device coordinates of 0 and +1 respectively. (Direct3D clip volume definition)
   *
   * @param fovy Specifies the field of view angle, in degrees, in the y direction. Expressed in radians.
   * @param aspect Specifies the aspect ratio that determines the field of view in the x direction. The aspect ratio is the ratio of x (width) to y (height).
   * @param zNear Specifies the distance from the viewer to the near clipping plane (always positive).
   * @param zFar Specifies the distance from the viewer to the far clipping plane (always positive).
   *
   */
  fun perspective(fovy: Float, aspect: Float, zNear: Float, zFar: Float): Mat4 {
    val tanHalfFovy = tan(fovy / (2))
    
    val result = Mat4()
    result[0, 0] = 1 / (aspect * tanHalfFovy)
    result[1, 1] = 1 / (tanHalfFovy)
    result[2, 2] = zFar / (zNear - zFar)
    result[2, 3] = -1f
    result[3, 2] = -(zFar * zNear) / (zFar - zNear)
    return result
  }
}