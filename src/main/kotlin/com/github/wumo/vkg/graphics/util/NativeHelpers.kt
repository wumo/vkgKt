package com.github.wumo.vkg.graphics.util

import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.Pointer

fun String.bytes() = BytePointer(*toByteArray(Charsets.UTF_8), 0)
inline fun <T> String.useNative(block: (BytePointer) -> T): T {
  val ptr = bytes()
  try {
    return block(ptr)
  } finally {
    ptr.deallocate()
  }
}

class NativeScope : AutoCloseable {
  private val dtors = mutableListOf<() -> Unit>()

  fun whenFinished(dtor: () -> Unit) {
    dtors += dtor
  }

  override fun close() {
    for (dtor in dtors)
      dtor()
  }
}

fun String.toNative(scope: NativeScope): BytePointer {
  val ptr = bytes()
  scope.whenFinished {
    ptr.deallocate()
  }
  return ptr
}

inline fun <reified T : Pointer> T.notNull(): T {
  check(!isNull) { "$this is null!" }
  return this
}

inline fun <R> native(block: (NativeScope) -> R): R {
  NativeScope().use {
    return block(it)
  }
}