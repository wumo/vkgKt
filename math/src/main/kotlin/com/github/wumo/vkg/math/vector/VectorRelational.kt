package com.github.wumo.vkg.math.vector

import com.github.wumo.vkg.math.common.epsilon
import kotlin.math.abs

object VectorRelational {
  fun epsilonEquals(x0: Float, x1: Float) = abs(x0 - x1) <= epsilon
  fun epsilonEquals(
    x0: Float, y0: Float,
    x1: Float, y1: Float
  ) = epsilonEquals(x0, x1) && epsilonEquals(y0, y1)

  fun epsilonEquals(
    x0: Float, y0: Float, z0: Float,
    x1: Float, y1: Float, z1: Float
  ) = epsilonEquals(x0, x1) && epsilonEquals(y0, y1) && epsilonEquals(z0, z1)

  fun epsilonEquals(
    x0: Float, y0: Float, z0: Float, w0: Float,
    x1: Float, y1: Float, z1: Float, w1: Float
  ) = epsilonEquals(x0, x1) && epsilonEquals(y0, y1)
      && epsilonEquals(z0, z1) && epsilonEquals(w0, w1)
}