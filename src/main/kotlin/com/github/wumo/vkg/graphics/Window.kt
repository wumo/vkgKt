package com.github.wumo.vkg.graphics

import com.github.wumo.vkg.graphics.VkgNative.*
import com.github.wumo.vkg.graphics.util.notNull

class Window(internal val native: CWindow) {
  
  val input = Input(WindowGetInput(native.notNull()))
  
  val width: Int = WindowGetWidth(native.notNull())
  val height: Int = WindowGetHeight(native.notNull())
  
  var title: String = run {
    val bytes = ByteArray(WindowGetTitleLength(native.notNull()))
    WindowGetTitle(native.notNull(), bytes);
    bytes.toString(Charsets.UTF_8)
  }
    set(value) {
      val bytes = value.toByteArray()
      WindowSetTitle(native.notNull(), bytes, bytes.size)
      field = value
    }
  
  fun close() {
    WindowClose(native.notNull())
  }
}