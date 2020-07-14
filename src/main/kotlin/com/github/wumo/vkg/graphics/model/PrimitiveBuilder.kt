package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.VkgNative.CPrimitiveBuilder
import com.github.wumo.vkg.graphics.util.notNull
import com.github.wumo.vkg.math.vector.Vec3

class PrimitiveBuilder(internal val native: CPrimitiveBuilder) {
  val numPrimitives: Int
    get() = BuilderNumPrimitives(native.notNull())

  fun from(positions: FloatArray, normals: FloatArray, uvs: FloatArray, indices: IntArray) {
    assert(positions.size % 3 == 0) { "positions.size %3 should equal 0" }
    assert(normals.size % 3 == 0) { "normals.size %3 should equal 0" }
    assert(uvs.size % 2 == 0) { "uvs.size %3 should equal 0" }
    assert(positions.size / 3 == normals.size / 3 && normals.size / 3 == uvs.size / 2) {
      "positions.size and normals.size and uvs should be the same"
    }
    PrimitiveBuilderFrom(native.notNull(),
        positions, 0, positions.size / 3,
        normals, 0, normals.size / 3,
        uvs, 0, uvs.size / 2,
        indices, indices.size)
  }

  fun newPrimitive(topology: PrimitiveTopology = PrimitiveTopology.Triangels,
                   type: DynamicType = DynamicType.Static) {
    BuildNewPrimitive(native.notNull(), topology.value, type.value)
  }

  fun triangle(p1: Vec3, p2: Vec3, p3: Vec3) {
    BuildTriangle(native.notNull(), p1.raw, p1.offset, p2.raw, p2.offset, p3.raw, p3.offset)
  }

  fun rectangle(center: Vec3, x: Vec3, y: Vec3) {
    BuildRectangle(native.notNull(), center.raw, center.offset, x.raw, x.offset, y.raw, y.offset)
  }

  fun box(center: Vec3, x: Vec3, y: Vec3, z: Float) {
    BuildBox(native.notNull(), center.raw, center.offset, x.raw, x.offset, y.raw, y.offset, z)
  }

  fun box(p1: Vec3, p2: Vec3, up: Vec3, width: Float, height: Float) {
    BuildBoxLine(native.notNull(), p1.raw, p1.offset, p2.raw, p2.offset, up.raw, up.offset, width, height)
  }

  fun checkerboard(
      nx: Int, ny: Int,
      center: Vec3 = Vec3(),
      x: Vec3 = Vec3(0f, 0f, 1f),
      y: Vec3 = Vec3(1f, 0f, 0f),
      wx: Float = 1f, wy: Float = 1f
  ) {
    BuildCheckerboard(native.notNull(), nx, ny, center.raw, center.offset, x.raw, x.offset, y.raw, y.offset, wx, wy)
  }

  fun sphere(center: Vec3, R: Float, nsubd: Int = 3) {
    BuildSphere(native.notNull(), center.raw, center.offset, R, nsubd)
  }

  fun cylinder(center: Vec3, z: Vec3, R: Float, cap: Boolean = true, segments: Int = 50) {
    BuildCylinder(native.notNull(), center.raw, center.offset, z.raw, z.offset, R, cap, segments)
  }

  fun axis(center: Vec3, length: Float, R: Float, capLength: Float, segments: Int = 50) {
    BuildAxis(native.notNull(), center.raw, center.offset, length, R, capLength, segments)
  }

  fun line(p1: Vec3, p2: Vec3) {
    BuildLine(native.notNull(), p1.raw, p1.offset, p2.raw, p2.offset)
  }
}