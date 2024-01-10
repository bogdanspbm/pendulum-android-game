package org.bogdanspbm.pendulum.models.hook

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import org.bogdanspbm.pendulum.models.pendulum.Pendulum
import org.bogdanspbm.pendulum.utils.fromHex

data class Hook(
    var x: Float = 0f,
    var y: Float = 0f,
    var radius: Float = 45f
) {
    fun distanceToPendulum(pendulum: Pendulum): Double {
        return Math.sqrt((pendulum.x - x).toDouble() * (pendulum.x - x).toDouble() + (pendulum.y - y).toDouble() * (pendulum.y - y).toDouble())
    }

    fun getAngleToPendulum(pendulum: Pendulum): Double {
        val deltaX = x - pendulum.x
        val deltaY = y - pendulum.y
        if (deltaX == 0f) {
            if (deltaY > 0) {
                return Math.PI / 2 // 90 degrees (or pi/2 radians)
            } else if (deltaY < 0) {
                return -Math.PI / 2 // -90 degrees (or -pi/2 radians)
            } else {
                return 0.0 // Both x and y are zero, so the angle is 0 degrees (or 0 radians)
            }
        }

        val angle = Math.atan2(deltaY.toDouble(), deltaX.toDouble())
        return angle
    }

    fun getRotateDirection(pendulum: Pendulum): Int {
        val deltaX = pendulum.x - x
        val deltaY = pendulum.y - y
        val z = -deltaX * pendulum.speedY + pendulum.speedX * deltaY
        val cosVal =
            z / (Math.sqrt(deltaX.toDouble() * deltaX + deltaY * deltaY) * pendulum.getSpeed())
        val angle = Math.acos(cosVal)
        return if (angle > Math.PI / 2) return 1 else -1
    }

    fun rotatePendulum(delta: Int, pendulum: Pendulum) {
        val distanceToPendulum = distanceToPendulum(pendulum)
        pendulum.angle += pendulum.rotationDirection * delta.toDouble() / Math.max(
            radius.toDouble() / 2,
            distanceToPendulum
        )

        val speedX = (x - Math.cos(pendulum.angle) * distanceToPendulum).toFloat() - pendulum.x
        val speedY = (y - Math.sin(pendulum.angle) * distanceToPendulum).toFloat() - pendulum.y
        val speed = Math.sqrt(speedX.toDouble() * speedX + speedY.toDouble() * speedY)

        pendulum.speedX = (speedX / speed).toFloat()
        pendulum.speedY = (speedY / speed).toFloat()

        pendulum.move(delta)

        Log.d(
            "PENDULUM",
            "${pendulum}"
        )
    }

    fun draw(scope: DrawScope, offset: Offset) {
        scope.drawCircle(
            color = Color.fromHex("#E1D03A"),
            radius = this.radius,
            center = Offset(
                x + scope.size.width / 2,
                scope.size.height / 2 - y
            ) + offset
        )

        scope.drawCircle(
            color = Color.fromHex("#ECE424"),
            radius = this.radius - 4,
            center = Offset(
                x + scope.size.width / 2,
                scope.size.height / 2 - y
            ) + offset
        )

        scope.drawCircle(
            color = Color.fromHex("#51336F"),
            radius = this.radius / 2,
            center = Offset(
                x + scope.size.width / 2,
                scope.size.height / 2 - y
            ) + offset
        )

        scope.drawCircle(
            color = Color.fromHex("#3C2D55"),
            radius = this.radius / 2 - 4,
            center = Offset(
                x + scope.size.width / 2,
                scope.size.height / 2 - y
            ) + offset
        )
    }
}