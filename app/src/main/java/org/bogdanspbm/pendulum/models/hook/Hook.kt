package org.bogdanspbm.pendulum.models.hook

import android.util.Log
import org.bogdanspbm.pendulum.models.pendulum.Pendulum

data class Hook(
    var x: Float = 0f,
    var y: Float = 0f,
    var radius: Float = 20f
) {
    fun distanceToPendulum(pendulum: Pendulum): Double {
        return Math.sqrt((pendulum.x - x).toDouble() * (pendulum.x - x).toDouble() + (pendulum.y - y).toDouble() * (pendulum.y - y).toDouble())
    }

    fun getAngleToPendulum(pendulum: Pendulum): Double {
        val deltaX = x - pendulum.x
        val deltaY = y - pendulum.y
        val tang = deltaY.toDouble() / deltaX.toDouble()
        val angle = Math.atan(tang) - Math.PI / 2
        return angle
    }

    fun getRotateDirection(pendulum: Pendulum): Int {
        val deltaX = x - pendulum.x
        val deltaY = y - pendulum.y
        val z = deltaX * pendulum.forwardY() - pendulum.forwardX() * deltaY
        Log.d("ASDASD", z.toString())
        return if (z > 0) return 1 else -1
    }

    fun rotatePendulum(delta: Int, pendulum: Pendulum) {
        val distanceToPendulum = distanceToPendulum(pendulum)
        pendulum.angle += delta.toDouble() * getRotateDirection(pendulum) / 1000
        pendulum.x = (x - Math.sin(pendulum.angle) * distanceToPendulum).toFloat()
        pendulum.y = (y - Math.cos(pendulum.angle) * distanceToPendulum).toFloat()
    }
}