package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.SceneManager
import com.github.wumo.vkg.graphics.VkgNative.NodeAddMeshes
import com.github.wumo.vkg.graphics.util.notNull

class Node(val scene: SceneManager, val id: Int) {

  fun addMeshes(vararg meshes: Mesh) {
    val _meshes = IntArray(meshes.size) {
      meshes[it].id
    }
    NodeAddMeshes(scene.native.notNull(), id, _meshes, _meshes.size)
  }
}