package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.SceneManager
import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.util.notNull

class Model(val scene: SceneManager, val id: Int) {
  val aabb: AABB = AABB().also { ModelGetAABB(scene.native.notNull(), id, it.raw) }
}