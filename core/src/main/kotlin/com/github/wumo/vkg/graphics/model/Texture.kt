package com.github.wumo.vkg.graphics.model

import com.github.wumo.vkg.graphics.Scene

enum class TextureFormat(val value: Int) {
  R8Unorm(0),
  R16Sfloat(1),
  R32Sfloat(2),
  R8G8B8A8Unorm(3),
  R16G16B16A16Sfloat(4),
  R32G32B32A32Sfloat(5)
}

class Texture(val scene: Scene, val id: Int) {
}