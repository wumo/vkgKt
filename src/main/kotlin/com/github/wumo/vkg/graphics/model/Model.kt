package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.Scene
import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.util.notNull

class Model(val scene: Scene, val id: Int) {
  val aabb: AABB
    get() = AABB().also { ModelGetAABB(scene.native.notNull(), id, it.raw) }
  
  val nodes: Array<Node>
    get() {
      val nodeIds = IntArray(ModelNumNodes(scene.native.notNull(), id))
      ModelGetNodes(scene.native.notNull(), id, nodeIds)
      return Array(nodeIds.size) {
        Node(scene, nodeIds[it])
      }
    }
}