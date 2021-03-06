package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.Scene
import com.github.wumo.vkg.graphics.VkgNative.MeshGetMaterial
import com.github.wumo.vkg.graphics.VkgNative.MeshGetPrimitive
import com.github.wumo.vkg.graphics.util.notNull

class Mesh(val scene: Scene, val id: Int) {
  val primitive: Primitive = Primitive(scene, MeshGetPrimitive(scene.native.notNull(), id))
  val material: Material = Material(scene, MeshGetMaterial(scene.native.notNull(), id))
}