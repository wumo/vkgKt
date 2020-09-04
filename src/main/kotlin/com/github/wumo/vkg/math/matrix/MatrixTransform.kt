package com.github.wumo.vkg.math.matrix

import com.github.wumo.vkg.math.vector.FuncGeometric.cross
import com.github.wumo.vkg.math.vector.FuncGeometric.dot
import com.github.wumo.vkg.math.vector.FuncGeometric.normalize
import com.github.wumo.vkg.math.vector.Vec3

object MatrixTransform {
  fun lookAt(eye: Vec3, center: Vec3, up: Vec3): Mat4 {
    val f = normalize(center - eye)
    val s = normalize(cross(f, up))
    val u = cross(s, f)
    val result = Mat4(1f)
    result[0, 0] = s.x
    result[1, 0] = s.y
    result[2, 0] = s.z
    result[0, 1] = u.x
    result[1, 1] = u.y
    result[2, 1] = u.z
    result[0, 2] = -f.x
    result[1, 2] = -f.y
    result[2, 2] = -f.z
    result[3, 0] = -dot(s, eye)
    result[3, 1] = -dot(u, eye)
    result[3, 2] = -dot(f, eye)
    return result
  }
}