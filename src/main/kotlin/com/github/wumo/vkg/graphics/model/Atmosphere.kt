package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.util.notNull
import com.github.wumo.vkg.math.vector.Vec3

class Atmosphere(internal val native: CAtmosphereSetting) {
  
  var enabled: Boolean = AtmosphereIsEnabled(native.notNull())
    set(value) {
      AtmosphereEnable(native.notNull(), value)
      field = value
    }
  var sunIntensity: Float = AtmosphereGetSunIntensity(native.notNull())
    set(value) {
      AtmosphereSetSunIntensity(native.notNull(), value)
      field = value
    }
  var sunDirection: Vec3 = Vec3().also { AtmosphereGetSunDirection(native.notNull(), it.raw, it.offset) }
    set(value) {
      AtmosphereSetSunDirection(native.notNull(), value.raw, value.offset)
      field.assign(value)
    }
  val earthRadius: Double = AtmosphereGetBottomRadius(native.notNull())
  var earthCenter: Vec3 = Vec3().also { AtmosphereGetEarthCenter(native.notNull(), it.raw, it.offset) }
    set(value) {
      AtmosphereSetEarthCenter(native.notNull(), value.raw, value.offset)
      field.assign(value)
    }
  val lengthUnitInMeters: Double
    get() = AtmosphereGetLengthUnitInMeters(native.notNull())
}