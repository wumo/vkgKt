package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.VkgNative.CCamera
import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.util.notNull
import com.github.wumo.vkg.math.matrix.Mat4
import com.github.wumo.vkg.math.vector.Vec3

class Camera(internal val native: CCamera) {
  var location: Vec3
    get() = Vec3().also { CameraGetLocation(native.notNull(), it.raw, it.offset) }
    set(value) {
      CameraSetLocation(native.notNull(), value.raw, value.offset)
    }
  var direction: Vec3
    get() = Vec3().also { CameraGetDirection(native.notNull(), it.raw, it.offset) }
    set(value) {
      CameraSetDirection(native.notNull(), value.raw, value.offset)
    }
  var worldUp: Vec3
    get() = Vec3().also { CameraGetWorldUp(native.notNull(), it.raw, it.offset) }
    set(value) {
      CameraSetWorldUp(native.notNull(), value.raw, value.offset)
    }
  var znear: Float = CameraGetZNear(native.notNull())
    set(value) {
      CameraSetZNear(native.notNull(), value)
      field = value
    }
  var zfar: Float = CameraGetZFar(native.notNull())
    set(value) {
      CameraSetZFar(native.notNull(), value)
      field = value
    }
  val fov: Float = CameraGetFov(native.notNull())
  val width: Int
    get() = CameraGetWidth(native.notNull())
  val height: Int
    get() = CameraGetHeight(native.notNull())
  val view: Mat4
    get() = Mat4().also { CameraGetView(native.notNull(), it.raw, it.offset) }
  val proj: Mat4
    get() = Mat4().also { CameraGetProj(native.notNull(), it.raw, it.offset) }
}