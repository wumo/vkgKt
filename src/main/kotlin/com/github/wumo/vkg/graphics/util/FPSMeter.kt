package com.github.wumo.vkg.graphics.util

import com.github.wumo.vkg.graphics.VkgNative.*

class FPSMeter(internal val native: CFPSMeter) {
  val fps: Int
    get() = FPSMeterGetFPS(native.notNull())
  val frameTime: Double
    get() = FPSMeterGetFrameTime(native.notNull())
}