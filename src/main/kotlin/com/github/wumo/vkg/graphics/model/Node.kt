package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.SceneManager
import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.util.notNull

class Node(val scene: SceneManager, val id: Int) {
  
  val transform: Transform = Transform(updateFunc = {
    NodeSetTransform(scene.native.notNull(), id, it.raw)
  }).also {
    NodeGetTransform(scene.native.notNull(), id, it.raw)
  }
  
  val parent: Node? = run {
    val parentID = NodeGetParent(scene.native.notNull(), id)
    if(parentID == CNullIdx) null
    else
      Node(scene, parentID)
  }
  
  val children: Array<Node>
    get() {
      val childIds = IntArray(NodeNumChildren(scene.native.notNull(), id))
      NodeGetChildren(scene.native.notNull(), id, childIds)
      return Array(childIds.size) {
        Node(scene, childIds[it])
      }
    }
  
  val meshes: Array<Mesh>
    get() {
      val meshIds = IntArray(NodeNumMeshes(scene.native.notNull(), id))
      NodeGetMeshes(scene.native.notNull(), id, meshIds)
      return Array(meshIds.size) {
        Mesh(scene, meshIds[it])
      }
    }
  
  var name: String = run {
    val size = NodeNameLength(scene.native.notNull(), id)
    val bytes = ByteArray(size)
    NodeGetName(scene.native.notNull(), id, bytes)
    bytes.toString(Charsets.UTF_8)
  }
    set(value) {
      val bytes = value.toByteArray()
      NodeSetName(scene.native.notNull(), id, bytes, bytes.size)
      field = value
    }
  
  val aabb: AABB = AABB().also {
    NodeGetAABB(scene.native.notNull(), id, it.raw)
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