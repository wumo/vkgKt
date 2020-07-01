package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.SceneManager
import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.util.notNull

class ModelInstance(val scene: SceneManager, val id: Int) {
  var transform: Transform = Transform()
    get() = Transform().also {
      ModelInstanceGetTransform(scene.native.notNull(), id, it.raw)
    }
    set(value) {
      ModelInstanceSetTransform(scene.native.notNull(), id, value.raw)
      field = value
    }
}