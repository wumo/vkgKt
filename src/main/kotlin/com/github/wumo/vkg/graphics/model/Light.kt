package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.SceneManager
import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.util.notNull
import com.github.wumo.vkg.math.vector.Vec3

class Light(val scene: SceneManager, val id: Int) {
  var color: Vec3 = Vec3().also { LightGetColor(scene.native.notNull(), id, it.raw, it.offset) }
    set(value) {
      LightSetColor(scene.native.notNull(), id, value.raw, value.offset)
      field.assign(value)
    }
  var location: Vec3 = Vec3().also { LightGetLocation(scene.native.notNull(), id, it.raw, it.offset) }
    set(value) {
      LightSetLocation(scene.native.notNull(), id, value.raw, value.offset)
      field.assign(value)
    }
  var intensity: Float = LightGetIntensity(scene.native.notNull(), id)
    set(value) {
      LightSetIntensity(scene.native.notNull(), id, value)
      field = value
    }
  var range: Float = LightGetRange(scene.native.notNull(), id)
    set(value) {
      LightSetRange(scene.native.notNull(), id, value)
      field = value
    }
}