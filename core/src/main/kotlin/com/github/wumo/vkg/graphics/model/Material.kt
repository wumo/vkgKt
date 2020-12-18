package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.Scene
import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.util.notNull
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
      return when(value) {
        CMaterialBRDF -> BRDF
        CMaterialBRDFSG -> BRDFSG
        CMaterialReflective -> Reflective
        CMaterialRefractive -> Refractive
        CMaterialNone -> None
        CMaterialTransparent -> Transparent
        CMaterialTerrain -> Terrain
        else                 -> error("Invalid value")
      }
    }
  }
}

class Material(val scene: Scene, val id: Int) {
  val count: Int = MaterialGetCount(scene.native.notNull(), id)
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
  var emissiveTex = Texture(scene, MaterialGetEmissiveTex(scene.native, id))
    set(value) {
      MaterialSetEmissiveTex(scene.native, id, value.id)
      field = value
    }
  var occlusionTex = Texture(scene, MaterialGetOcclusionTex(scene.native, id))
    set(value) {
      MaterialSetOcclusionTex(scene.native, id, value.id)
      field = value
    }
  var normalTex = Texture(scene, MaterialGetNormalTex(scene.native, id))
    set(value) {
      MaterialSetNormalTex(scene.native, id, value.id)
      field = value
    }
  var colorFactor = Vec4().also { MaterialGetColorFactor(scene.native, id, it.raw, it.offset) }
    set(value) {
      MaterialSetColorFactor(scene.native, id, value.raw, value.offset)
      field.assign(value)
    }
  var pbrFactor = Vec4().also { MaterialGetPbrFactor(scene.native, id, it.raw, it.offset) }
    set(value) {
      MaterialSetPbrFactor(scene.native, id, value.raw, value.offset)
      field.assign(value)
    }
  var emissiveFactor = Vec4().also { MaterialGetEmissiveFactor(scene.native, id, it.raw, it.offset) }
    set(value) {
      MaterialSetEmissiveFactor(scene.native, id, value.raw, value.offset)
      field.assign(value)
    }
  var occlusionStrength = MaterialGetOcclusionStrength(scene.native, id)
    set(value) {
      MaterialSetOcclusionStrength(scene.native, id, value)
      field = value
    }
  var alphaCutOff = MaterialGetAlphaCutoff(scene.native, id)
    set(value) {
      MaterialSetAlphaCutoff(scene.native, id, value)
      field = value
    }
  val type = MaterialType.fromValue(MaterialGetType(scene.native, id))
}