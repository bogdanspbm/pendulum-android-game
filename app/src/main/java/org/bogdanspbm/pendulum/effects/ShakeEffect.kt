package org.bogdanspbm.pendulum.effects

import android.util.Log
import androidx.compose.ui.geometry.Offset
import kotlin.math.PI
import kotlin.math.sin


val TAG = "ShakeEffect"

class ShakeEffect(val amplitude: Float = 10f) {
    var offset: Offset = Offset(0f, 0f)
    var startShakeTick: Long = 0L
    var enabled: Boolean = false

    fun enable(startTick: Long) {
        if (enabled) {
            return
        }

        startShakeTick = startTick
        enabled = true
    }

    fun tickEvent(tick: Long) {
        if (!enabled) {
            return
        }

        var deltaTick = tick - startShakeTick
        val maxW = 32 * PI * 2

        if (deltaTick > maxW) {
            deltaTick = maxW.toLong()
        }

        val offsetX = amplitude * sin(deltaTick.toDouble() / 16)
        val offsetY = amplitude * sin(deltaTick.toDouble() / 32)

        offset = Offset(offsetX.toFloat(), offsetY.toFloat())
        Log.d(TAG, "${offset.x}:${offset.y}")
    }
}