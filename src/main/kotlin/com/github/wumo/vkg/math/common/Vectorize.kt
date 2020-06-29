package com.github.wumo.vkg.math.common

interface Vectorizable<V : Vectorizable<V>> {
  fun newVec(): V
  fun rawSize(): Int
  fun rawGet(idx: Int): Float
  fun rawSet(idx: Int, v: Float)
}
