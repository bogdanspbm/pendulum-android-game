package org.bogdanspbm.pendulum.models.pendulum

import androidx.compose.ui.geometry.Offset
import org.bogdanspbm.pendulum.models.game.GameState
import kotlin.math.abs

data class Pendulum(
    var x: Float = 0f,
    var y: Float = 0f,
    var speedY: Float = 1f,
    var speedX: Float = 0f,
    var angle: Double = 0.toDouble(),
    var rotationDirection: Int = 1,
    var radius: Float = 30f,
    var prevPositions: MutableList<Offset> = mutableListOf()
) {
    fun move(delta: Int) {
        prevPositions.add(Offset(x, -y))

        if (prevPositions.size > 100) {
            prevPositions.removeAt(0)
        }

        x += speedX * delta
        y += speedY * delta
    }

    fun getSpeed(): Float {
        return Math.sqrt(speedY.toDouble() * speedY + speedX.toDouble() * speedX).toFloat()
    }



}