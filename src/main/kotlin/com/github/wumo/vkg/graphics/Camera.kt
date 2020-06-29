package com.github.wumo.vkg.graphics

import com.github.wumo.vkg.graphics.VkgNative.CCamera
import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.util.notNull
import com.github.wumo.vkg.math.matrix.Mat4
import com.github.wumo.vkg.math.matrix.MatrixTransform.lookAt
import com.github.wumo.vkg.math.vector.Vec3

class Camera(internal val native: CCamera) {
  var location: Vec3 = Vec3().also { GetCameraLocation(native.notNull(), it.raw) }
    set(value) {
      SetCameraLocation(native.notNull(), value.raw)
      field = value
    }
  var direction: Vec3 = Vec3().also { GetCameraDirection(native.notNull(), it.raw) }
    set(value) {
      SetCameraDirection(native.notNull(), value.raw)
      field = value
    }
  var worldUp: Vec3 = Vec3().also { GetCameraWorldUp(native.notNull(), it.raw) }
    set(value) {
      SetCameraWorldUp(native.notNull(), value.raw)
      field = value
    }
  var znear: Float = GetCameraZNear(native.notNull())
    set(value) {
      SetCameraZNear(native.notNull(), value)
      field = value
    }
  var zfar: Float = GetCameraZFar(native.notNull())
    set(value) {
      SetCameraZFar(native.notNull(), value)
      field = value
    }
  val width: Int
    get() = GetCameraWidth(native.notNull())
  val height: Int
    get() = GetCameraHeight(native.notNull())
  val view: Mat4
    get() = Mat4().also { GetCameraView(native.notNull(), it.raw) }
  val proj: Mat4
    get() = Mat4().also { GetCameraProj(native.notNull(), it.raw) }
}