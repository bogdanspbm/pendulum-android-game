package org.bogdanspbm.pendulum.utils

import android.graphics.Matrix
import androidx.compose.ui.geometry.Offset

fun Offset.rotateOffset(degree: Float): Offset{
    val matrix = Matrix()
    matrix.postRotate(degree)
    val result = FloatArray(2)
    matrix.mapPoints(result, floatArrayOf(x, y))
    return Offset(result[0], result[1])
}