package org.bogdanspbm.pendulum.models.pendulum

data class Pendulum(
    var x: Float = 0f,
    var y: Float = 0f,
    var speed: Float = 1f,
    var angle: Double = 0.toDouble(),
    var radius: Float = 30f
) {
    fun move(delta: Int) {
        x += Math.sin(angle).toFloat() * speed * delta
        y += Math.cos(angle).toFloat() * speed * delta
    }

    fun forwardY(): Float {
        return Math.cos(angle).toFloat()
    }

    fun forwardX(): Float {
        return Math.sin(angle).toFloat()
    }

}