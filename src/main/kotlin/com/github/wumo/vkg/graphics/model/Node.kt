package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.SceneManager
import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.util.notNull

class Node(val scene: SceneManager, val id: Int) {

  var transform: Transform = Transform()
    get() = Transform().also {
      NodeGetTransform(scene.native.notNull(), id, it.raw)
    }
    set(value) {
      NodeSetTransform(scene.native.notNull(), id, value.raw)
      field = value
    }

  fun addMeshes(vararg meshes: Mesh) {
    val _meshes = IntArray(meshes.size) {
      meshes[it].id
    }
    NodeAddMeshes(scene.native.notNull(), id, _meshes, _meshes.size)
  }

  fun addChildren(vararg children: Node) {
    val children_ = IntArray(children.size) {
      children[it].id
    }
    NodeAddChildren(scene.native.notNull(), id, children_, children_.size)
  }
}