package com.github.wumo.vkg.graphics

import com.github.wumo.vkg.graphics.VkgNative.CWindow
import com.github.wumo.vkg.graphics.VkgNative.WindowGetInput
import com.github.wumo.vkg.graphics.util.notNull

class Window(internal val native: CWindow) {

  val input = Input(WindowGetInput(native.notNull()))

}