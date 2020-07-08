package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.SceneManager
import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.util.notNull

class ModelInstance(val scene: SceneManager, val id: Int) {
  val transform: Transform = Transform(updateFunc = {
    ModelInstanceSetTransform(scene.native.notNull(), id, it.raw)
  }).also {
    ModelInstanceGetTransform(scene.native.notNull(), id, it.raw)
  }
  
  val model: Model = Model(scene, ModelInstanceGetModel(scene.native.notNull(), id))
}