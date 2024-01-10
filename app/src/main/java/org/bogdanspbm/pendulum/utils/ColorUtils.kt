package org.bogdanspbm.pendulum.utils

import androidx.compose.ui.graphics.Color

fun Color.Companion.fromHex(hex: String): Color {
    val sanitizedHex = if (hex.startsWith("#")) hex.substring(1) else hex
    val colorInt = java.lang.Long.parseLong(sanitizedHex, 16)
    val alpha = (colorInt shr 24 and 0xFF) / 255.0f
    val red = (colorInt shr 16 and 0xFF) / 255.0f
    val green = (colorInt shr 8 and 0xFF) / 255.0f
    val blue = (colorInt and 0xFF) / 255.0f
    return Color(red, green, blue, 1f)
}

fun lerpColor(colorA: Color, colorB: Color, value: Float): Color {
    val clampedValue = value.coerceIn(0f, 1f)

    val red = (colorA.red * (1 - clampedValue) + colorB.red * clampedValue)
    val green = (colorA.green * (1 - clampedValue) + colorB.green * clampedValue)
    val blue = (colorA.blue * (1 - clampedValue) + colorB.blue * clampedValue)

    return Color(red, green, blue)
}