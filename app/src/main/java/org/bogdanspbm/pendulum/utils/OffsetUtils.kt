package org.bogdanspbm.pendulum.utils

import android.content.Context
import android.graphics.Matrix
import android.util.TypedValue
import androidx.compose.ui.geometry.Offset

fun Offset.rotateOffset(degree: Float): Offset{
    val matrix = Matrix()
    matrix.postRotate(degree)
    val result = FloatArray(2)
    matrix.mapPoints(result, floatArrayOf(x, y))
    return Offset(result[0], result[1])
}

fun dpToPx(context: Context, dp: Float): Float {
    val displayMetrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)
}

fun pxToDp(context: Context, px: Float): Float {
    val displayMetrics = context.resources.displayMetrics
    return px.toFloat() / displayMetrics.density
}