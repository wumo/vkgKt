package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.SceneManager
import com.github.wumo.vkg.graphics.VkgNative
import com.github.wumo.vkg.graphics.VkgNative.PrimitiveGetIndex
import com.github.wumo.vkg.graphics.util.Range

enum class PrimitiveTopology(val value: Long) {
  Triangels(VkgNative.CPrimitiveTriangles),
  Lines(VkgNative.CPrimitiveLines),
  Procedural(VkgNative.CPrimitiveProcedural),
  Patches(VkgNative.CPrimitivePatches)
}

class Primitive(val scene: SceneManager, val id: Int) {
  val index: Range = PrimitiveGetIndex(scene.native, id).let {
    Range(it.start(), it.size())
  }
  //TODO
}