package com.github.wumo.vkg.graphics

import com.github.wumo.vkg.graphics.model.Material
import com.github.wumo.vkg.graphics.model.Model
import com.github.wumo.vkg.graphics.model.Primitive

fun Scene.newModel(primitive: Primitive, material: Material): Model {
  val mesh = newMesh(primitive, material)
  val node = newNode()
  node.addMeshes(mesh)
  return newModel(node)
}