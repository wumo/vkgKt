package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.VkgNative
import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.util.notNull

class ShadowMap(internal val native: VkgNative.CShadowMapSetting) {
  
  var enabled: Boolean = ShadowMapIsEnabled(native.notNull())
    set(value) {
      ShadowMapEnable(native.notNull(), value)
      field = value
    }
  
  var numCascades: Int = ShadowMapGetNumCascades(native.notNull())
    set(value) {
      ShadowMapSetNumCascades(native.notNull(), value)
      field = value
    }
  
  var textureSize: Int = ShadowMapGetTextureSize(native.notNull())
    set(value) {
      ShadowMapSetTextureSize(native.notNull(), value)
      field = value
    }
  
  var zFar: Float = ShadowMapGetZFar(native.notNull())
    set(value) {
      ShadowMapSetZFar(native.notNull(), value)
      field = value
    }
}