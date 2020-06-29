package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.SceneManager
import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.math.vector.Vec4

enum class MaterialType(val value: Int) {
  BRDF(CMaterialBRDF),
  BRDFSG(CMaterialBRDFSG),
  Reflective(CMaterialReflective),
  Refractive(CMaterialRefractive),
  None(CMaterialNone),
  Transparent(CMaterialTransparent),
  Terrain(CMaterialTerrain);

  companion object {
    fun fromValue(value: Int): MaterialType {
      return when (value) {
        CMaterialBRDF -> BRDF
        CMaterialBRDFSG -> BRDFSG
        CMaterialReflective -> Reflective
        CMaterialRefractive -> Refractive
        CMaterialNone -> None
        CMaterialTransparent -> Transparent
        CMaterialTerrain -> Terrain
        else -> error("Invalid value")
      }
    }
  }
}

class Material(val scene: SceneManager, val id: Int) {
  var colorTex = Texture(scene, MaterialGetColorTex(scene.native, id))
    set(value) {
      MaterialSetColorTex(scene.native, id, value.id)
      field = value
    }
  var pbrTex = Texture(scene, MaterialGetPbrTex(scene.native, id))
    set(value) {
      MaterialSetPbrTex(scene.native, id, value.id)
      field = value
    }
  var normalTex = Texture(scene, MaterialGetNormalTex(scene.native, id))
    set(value) {
      MaterialSetNormalTex(scene.native, id, value.id)
      field = value
    }
  var colorFactor = Vec4().also { MaterialGetColorFactor(scene.native, id, it.raw) }
    set(value) {
      MaterialSetColorFactor(scene.native, id, value.raw)
      field = value
    }
  var pbrFactor = Vec4().also { MaterialGetPbrFactor(scene.native, id, it.raw) }
    set(value) {
      MaterialSetPbrFactor(scene.native, id, value.raw)
      field = value
    }
  val type = MaterialType.fromValue(MaterialGetType(scene.native, id))
}