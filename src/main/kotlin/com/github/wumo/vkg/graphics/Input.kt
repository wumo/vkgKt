package com.github.wumo.vkg.graphics

import com.github.wumo.vkg.graphics.VkgNative.Input

class Input(internal val native: Input) {
  val mousePosX: Int
    get() = native.mousePosX()
  val mousePosY: Int
    get() = native.mousePosY()
  var scrollOffsetX: Double
    get() = native.scrollXOffset()
    set(value) {
      native.scrollXOffset(value)
    }
  var scrollOffsetY: Double
    get() = native.scrollYOffset()
    set(value) {
      native.scrollYOffset(value)
    }
  
  fun isKeyPressed(key: Key): Boolean {
    return native.keyPressed(key.value)
  }
  
  fun isMouseButtonPressed(button: MouseButton): Boolean {
    return native.mouseButtonPressed(button.value)
  }
}

enum class MouseButton(val value: Int) {
  MouseButtonLeft(VkgNative.MouseButtonLeft),
  MouseButtonRight(VkgNative.MouseButtonRight),
  MouseButtonMiddle(VkgNative.MouseButtonMiddle),
}

enum class Key(val value: Int) {
  KeySPACE(VkgNative.KeySPACE),
  KeyAPOSTROPHE(VkgNative.KeyAPOSTROPHE), /* ' */
  KeyCOMMA(VkgNative.KeyCOMMA),      /* , */
  KeyMINUS(VkgNative.KeyMINUS),      /* - */
  KeyPERIOD(VkgNative.KeyPERIOD),     /* . */
  KeySLASH(VkgNative.KeySLASH),      /* / */
  Key0(VkgNative.Key0),
  Key1(VkgNative.Key1),
  Key2(VkgNative.Key2),
  Key3(VkgNative.Key3),
  Key4(VkgNative.Key4),
  Key5(VkgNative.Key5),
  Key6(VkgNative.Key6),
  Key7(VkgNative.Key7),
  Key8(VkgNative.Key8),
  Key9(VkgNative.Key9),
  KeySEMICOLON(VkgNative.KeySEMICOLON), /* ; */
  KeyEQUAL(VkgNative.KeyEQUAL),     /* = */
  KeyA(VkgNative.KeyA),
  KeyB(VkgNative.KeyB),
  KeyC(VkgNative.KeyC),
  KeyD(VkgNative.KeyD),
  KeyE(VkgNative.KeyE),
  KeyF(VkgNative.KeyF),
  KeyG(VkgNative.KeyG),
  KeyH(VkgNative.KeyH),
  KeyI(VkgNative.KeyI),
  KeyJ(VkgNative.KeyJ),
  KeyK(VkgNative.KeyK),
  KeyL(VkgNative.KeyL),
  KeyM(VkgNative.KeyM),
  KeyN(VkgNative.KeyN),
  KeyO(VkgNative.KeyO),
  KeyP(VkgNative.KeyP),
  KeyQ(VkgNative.KeyQ),
  KeyR(VkgNative.KeyR),
  KeyS(VkgNative.KeyS),
  KeyT(VkgNative.KeyT),
  KeyU(VkgNative.KeyU),
  KeyV(VkgNative.KeyV),
  KeyW(VkgNative.KeyW),
  KeyX(VkgNative.KeyX),
  KeyY(VkgNative.KeyY),
  KeyZ(VkgNative.KeyZ),
  KeyLEFT_BRACKET(VkgNative.KeyLEFT_BRACKET),  /* [ */
  KeyBACKSLASH(VkgNative.KeyBACKSLASH),     /* \ */
  KeyRIGHT_BRACKET(VkgNative.KeyRIGHT_BRACKET), /* ] */
  KeyGRAVE_ACCENT(VkgNative.KeyGRAVE_ACCENT),  /* ` */
  KeyWORLD_1(VkgNative.KeyWORLD_1),       /* non-US #1 */
  KeyWORLD_2(VkgNative.KeyWORLD_2),       /* non-US #2 */
  
  /* FunctionKeys */
  KeyESCAPE(VkgNative.KeyESCAPE),
  KeyENTER(VkgNative.KeyENTER),
  KeyTAB(VkgNative.KeyTAB),
  KeyBACKSPACE(VkgNative.KeyBACKSPACE),
  KeyINSERT(VkgNative.KeyINSERT),
  KeyDELETE(VkgNative.KeyDELETE),
  KeyRIGHT(VkgNative.KeyRIGHT),
  KeyLEFT(VkgNative.KeyLEFT),
  KeyDOWN(VkgNative.KeyDOWN),
  KeyUP(VkgNative.KeyUP),
  KeyPAGE_UP(VkgNative.KeyPAGE_UP),
  KeyPAGE_DOWN(VkgNative.KeyPAGE_DOWN),
  KeyHOME(VkgNative.KeyHOME),
  KeyEND(VkgNative.KeyEND),
  KeyCAPS_LOCK(VkgNative.KeyCAPS_LOCK),
  KeySCROLL_LOCK(VkgNative.KeySCROLL_LOCK),
  KeyNUM_LOCK(VkgNative.KeyNUM_LOCK),
  KeyPRINT_SCREEN(VkgNative.KeyPRINT_SCREEN),
  KeyPAUSE(VkgNative.KeyPAUSE),
  KeyF1(VkgNative.KeyF1),
  KeyF2(VkgNative.KeyF2),
  KeyF3(VkgNative.KeyF3),
  KeyF4(VkgNative.KeyF4),
  KeyF5(VkgNative.KeyF5),
  KeyF6(VkgNative.KeyF6),
  KeyF7(VkgNative.KeyF7),
  KeyF8(VkgNative.KeyF8),
  KeyF9(VkgNative.KeyF9),
  KeyF10(VkgNative.KeyF10),
  KeyF11(VkgNative.KeyF11),
  KeyF12(VkgNative.KeyF12),
  KeyF13(VkgNative.KeyF13),
  KeyF14(VkgNative.KeyF14),
  KeyF15(VkgNative.KeyF15),
  KeyF16(VkgNative.KeyF16),
  KeyF17(VkgNative.KeyF17),
  KeyF18(VkgNative.KeyF18),
  KeyF19(VkgNative.KeyF19),
  KeyF20(VkgNative.KeyF20),
  KeyF21(VkgNative.KeyF21),
  KeyF22(VkgNative.KeyF22),
  KeyF23(VkgNative.KeyF23),
  KeyF24(VkgNative.KeyF24),
  KeyF25(VkgNative.KeyF25),
  KeyKP_0(VkgNative.KeyKP_0),
  KeyKP_1(VkgNative.KeyKP_1),
  KeyKP_2(VkgNative.KeyKP_2),
  KeyKP_3(VkgNative.KeyKP_3),
  KeyKP_4(VkgNative.KeyKP_4),
  KeyKP_5(VkgNative.KeyKP_5),
  KeyKP_6(VkgNative.KeyKP_6),
  KeyKP_7(VkgNative.KeyKP_7),
  KeyKP_8(VkgNative.KeyKP_8),
  KeyKP_9(VkgNative.KeyKP_9),
  KeyKP_DECIMAL(VkgNative.KeyKP_DECIMAL),
  KeyKP_DIVIDE(VkgNative.KeyKP_DIVIDE),
  KeyKP_MULTIPLY(VkgNative.KeyKP_MULTIPLY),
  KeyKP_SUBTRACT(VkgNative.KeyKP_SUBTRACT),
  KeyKP_ADD(VkgNative.KeyKP_ADD),
  KeyKP_ENTER(VkgNative.KeyKP_ENTER),
  KeyKP_EQUAL(VkgNative.KeyKP_EQUAL),
  KeyLEFT_SHIFT(VkgNative.KeyLEFT_SHIFT),
  KeyLEFT_CONTROL(VkgNative.KeyLEFT_CONTROL),
  KeyLEFT_ALT(VkgNative.KeyLEFT_ALT),
  KeyLEFT_SUPER(VkgNative.KeyLEFT_SUPER),
  KeyRIGHT_SHIFT(VkgNative.KeyRIGHT_SHIFT),
  KeyRIGHT_CONTROL(VkgNative.KeyRIGHT_CONTROL),
  KeyRIGHT_ALT(VkgNative.KeyRIGHT_ALT),
  KeyRIGHT_SUPER(VkgNative.KeyRIGHT_SUPER),
  KeyMENU(VkgNative.KeyMENU),
}