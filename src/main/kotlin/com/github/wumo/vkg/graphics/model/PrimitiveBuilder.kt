package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.VkgNative
import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.VkgNative.CPrimitiveBuilder
import com.github.wumo.vkg.graphics.util.notNull
import com.github.wumo.vkg.math.vector.Vec3

class PrimitiveBuilder(internal val native: CPrimitiveBuilder) {
  val numPrimitives: Int
    get() = VkgNative.BuilderNumPrimitives(native.notNull())

  fun newPrimitive(topology: PrimitiveTopology = PrimitiveTopology.Triangels) {
    BuildNewPrimitive(native.notNull(), topology.value)
  }

  fun rectangle(center: Vec3, x: Vec3, y: Vec3) {
    BuildRectangle(native.notNull(), center.raw, x.raw, y.raw)
  }

  fun checkerboard(
    nx: Int, ny: Int,
    center: Vec3 = Vec3(),
    x: Vec3 = Vec3(0f, 0f, 1f),
    y: Vec3 = Vec3(1f, 0f, 0f),
    wx: Float = 1f, wy: Float = 1f
  ) {
    BuildCheckerboard(native.notNull(), nx, ny, center.raw, x.raw, y.raw, wx, wy)
  }

  fun sphere(center: Vec3, R: Float, nsubd: Int = 3) {
    BuildSphere(native.notNull(), center.raw, R, nsubd)
  }

  fun box(center: Vec3, x: Vec3, y: Vec3, z: Float) {
    BuildBox(native.notNull(), center.raw, x.raw, y.raw, z)
  }

  fun axis(center: Vec3, length: Float, R: Float, capLength: Float, segments: Int = 50) {
    BuildAxis(native.notNull(), center.raw, length, R, capLength, segments)
  }
}