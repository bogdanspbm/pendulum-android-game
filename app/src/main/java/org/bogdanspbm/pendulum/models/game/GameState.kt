package org.bogdanspbm.pendulum.models.game

import org.bogdanspbm.pendulum.models.pendulum.Pendulum
import java.util.Date
import kotlin.math.sin

data class GameState(
    val time: Long = Date().time,
    var tick: Long = 0L,
    val pendulum: Pendulum = Pendulum()
) {
    fun tickEvent(delta: Int) {
        tick += delta

        if (!isPointerDown) {
            return
        }

        pendulum.x = sin(tick.toDouble() / 100).toFloat() * 100
    }

    companion object{
        var isPointerDown = false
    }
}