package com.github.wumo.vkg.graphics

import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.util.notNull
import com.github.wumo.vkg.math.vector.Vec3

class Atmosphere(internal val native: CAtmosphere) {
  fun init(kLengthUnitInMeters: Double = 1000.0, kSunAngularRadius: Double = 0.00935 / 2.0) {
    AtmosphereInit(native.notNull(), kLengthUnitInMeters, kSunAngularRadius)
  }

  val isEnabled: Boolean
    get() = AtmosphereIsEnabled(native.notNull())
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
  val earthRadius: Double = AtmosphereGetEarthRadius(native.notNull())
  var earthCenter: Vec3 = Vec3().also { AtmosphereGetEarthCenter(native.notNull(), it.raw, it.offset) }
    set(value) {
      AtmosphereSetEarthCenter(native.notNull(), value.raw, value.offset)
      field.assign(value)
    }
  val lengthUnitInMeters: Double
    get() = AtmosphereGetLengthUnitInMeters(native.notNull())
}