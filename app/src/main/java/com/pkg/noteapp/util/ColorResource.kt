package com.pkg.noteapp.util

sealed class ColorResource(val value: Long) {
    object Yellow : ColorResource(value = 0xFFFFFF00)
    object Blue : ColorResource(value = 0xFF0000FF)
    object Magenta : ColorResource(value = 0xFFFF00FF)
    object Cyan : ColorResource(value = 0xFF00FFFF)
    object White : ColorResource(value = 0xFFFFFFFF)

    companion object {
        fun getResourceFromColor(value: Long): ColorResource {
            return when (value) {
                0xFFFFFF00 -> Yellow
                0xFF0000FF -> Blue
                0xFFFF00FF -> Magenta
                0xFF00FFFF -> Cyan
                else -> White
            }
        }
    }
}
