package org.bogdanspbm.pendulum.effects

import android.util.Log
import androidx.compose.ui.geometry.Offset
import kotlin.math.sin


val TAG = "ShakeEffect"

class ShakeEffect(val amplitude: Float = 5f) {
    var offset: Offset = Offset(0f, 0f)
    var startShakeTick: Long = 0L
    var enabled: Boolean = false

    fun enable(startTick: Long) {
        startShakeTick = startTick
        enabled = true
    }

    fun tickEvent(tick: Long) {
        if (enabled) {
            return
        }

        val deltaTick = tick - startShakeTick

        val offsetX = amplitude * sin(deltaTick.toDouble() / 16)
        val offsetY = amplitude * sin(deltaTick.toDouble() / 32)

        offset = Offset(offsetX.toFloat(), offsetY.toFloat())
        Log.d(TAG, "${offset.x}:${offset.y}")
    }
}