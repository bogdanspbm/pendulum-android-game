package org.bogdanspbm.pendulum.models.pendulum

data class Pendulum(
    var x: Float = 0f,
    var y: Float = 0f,
    var speedY: Float = 1f,
    var speedX: Float = 0f,
    var angle: Double = 0.toDouble(),
    var rotationDirection: Int = 1,
    var radius: Float = 30f
) {
    fun move(delta: Int) {
        x += speedX * delta
        y += speedY * delta
    }

    fun getSpeed(): Float {
        return Math.sqrt(speedY.toDouble() * speedY + speedX.toDouble() * speedX).toFloat()
    }

}