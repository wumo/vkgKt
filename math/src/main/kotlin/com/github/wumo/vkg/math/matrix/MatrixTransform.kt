package com.github.wumo.vkg.math.matrix

import com.github.wumo.vkg.math.quat.FuncQuat.mat4_cast
import com.github.wumo.vkg.math.quat.Quat
import com.github.wumo.vkg.math.vector.FuncGeometric.cross
import com.github.wumo.vkg.math.vector.FuncGeometric.dot
import com.github.wumo.vkg.math.vector.FuncGeometric.normalize
import com.github.wumo.vkg.math.vector.Vec3
import com.github.wumo.vkg.math.vector.Vec4

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

  fun scale_(result: Mat4, v: Vec3): Mat4 {
    result.raw[result.offset + 0] *= v.raw[v.offset + 0]
    result.raw[result.offset + 1] *= v.raw[v.offset + 0]
    result.raw[result.offset + 2] *= v.raw[v.offset + 0]
    result.raw[result.offset + 3] *= v.raw[v.offset + 0]
    result.raw[result.offset + 4] *= v.raw[v.offset + 1]
    result.raw[result.offset + 5] *= v.raw[v.offset + 1]
    result.raw[result.offset + 6] *= v.raw[v.offset + 1]
    result.raw[result.offset + 7] *= v.raw[v.offset + 1]
    result.raw[result.offset + 8] *= v.raw[v.offset + 2]
    result.raw[result.offset + 9] *= v.raw[v.offset + 2]
    result.raw[result.offset + 10] *= v.raw[v.offset + 2]
    result.raw[result.offset + 11] *= v.raw[v.offset + 2]
    return result
  }

  fun scale(m: Mat4, v: Vec3): Mat4 {
    return scale_(m.copy(), v)
  }
}