package com.github.wumo.vkg.graphics.util

import com.github.wumo.vkg.graphics.Camera
import com.github.wumo.vkg.graphics.Input
import com.github.wumo.vkg.graphics.MouseButton
import com.github.wumo.vkg.math.matrix.FuncMatrix.inverse
import com.github.wumo.vkg.math.quat.QuatTrigonometric.angleAxis
import com.github.wumo.vkg.math.vector.FuncGeometric.angle
import com.github.wumo.vkg.math.vector.FuncGeometric.clamp
import com.github.wumo.vkg.math.vector.FuncGeometric.cross
import com.github.wumo.vkg.math.vector.FuncGeometric.length
import com.github.wumo.vkg.math.vector.FuncGeometric.normalize
import com.github.wumo.vkg.math.vector.FuncTrigonometric.degrees
import com.github.wumo.vkg.math.vector.FuncTrigonometric.radians
import com.github.wumo.vkg.math.vector.Vec2
import com.github.wumo.vkg.math.vector.Vec3
import com.github.wumo.vkg.math.vector.Vec4

class PanningCamera(val camera: Camera) {
  private class MouseAction {
    var lastX = 0f
    var lastY = 0f
    var start = false
    var sensitivity = 0.1f
  }
  
  private val rotate = MouseAction()
  private val panning = MouseAction()
  
  private var mouseRightPressed = false
  
  fun update(input: Input) {
    val front = camera.direction
    val len = length(front)
    camera.location = camera.location + normalize(front) * len *
        clamp(input.scrollOffsetY.toFloat() / 10, -1f, 1f)
    input.scrollOffsetY = 0.0
    
    val right = normalize(cross(front, camera.worldUp))
    
    if(input.isMouseButtonPressed(MouseButton.MouseButtonLeft)) {
      if(!rotate.start) {
        rotate.lastX = input.mousePosX.toFloat()
        rotate.lastY = input.mousePosY.toFloat()
        rotate.start = true
      }
      var xoffset = input.mousePosX - rotate.lastX
      var yoffset = input.mousePosY - rotate.lastY
      rotate.lastX = input.mousePosX.toFloat()
      rotate.lastY = input.mousePosY.toFloat()
      
      xoffset *= -rotate.sensitivity
      yoffset *= -rotate.sensitivity
      
      val focus = camera.location + camera.direction
      val translation = -front
      var pitch = degrees(angle(camera.worldUp, normalize(translation)))
      pitch += yoffset
      if(pitch <= 1 || pitch >= 179) yoffset = 0f
      camera.location = focus + angleAxis(radians(xoffset), camera.worldUp) *
          angleAxis(radians(yoffset), right) * translation
    } else
      rotate.start = false
    
    if(input.isMouseButtonPressed(MouseButton.MouseButtonRight)) {
      mouseRightPressed = true
      if(!panning.start) {
        panning.lastX = input.mousePosX.toFloat()
        panning.lastY = input.mousePosY.toFloat()
        panning.start = true
      }
      val p0 = xzIntersection(panning.lastX, panning.lastY)
      val p1 = xzIntersection(input.mousePosX.toFloat(), input.mousePosY.toFloat())
      
      panning.lastX = input.mousePosX.toFloat()
      panning.lastY = input.mousePosY.toFloat()
      
      val translation = p0 - p1
      camera.direction = camera.direction + translation
      camera.location = camera.location + translation
    } else {
      if(mouseRightPressed)
        panning.start = false
      mouseRightPressed = false
    }
  }
  
  private fun xzIntersection(inputX: Float, inputY: Float): Vec3 {
    val view = camera.view
    val viewInv = inverse(view)
    val proj = camera.proj
    var c = (Vec2(inputX, inputY) + 0.5f) / Vec2(camera.width.toFloat(), camera.height.toFloat())
    c = c * 2f - 1f
    val projInv = inverse(proj)
    val target = Vec3(projInv * Vec4(c.x, -c.y, 0f, 1f))
    val dir = Vec3(viewInv * Vec4(normalize(target), 0f))
    if(dir.y == 0f) return camera.location + camera.direction
    val t = -camera.location.y / dir.y
    return camera.location + dir * t
  }
}