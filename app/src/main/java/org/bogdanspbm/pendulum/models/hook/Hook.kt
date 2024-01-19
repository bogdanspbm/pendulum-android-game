package org.bogdanspbm.pendulum.models.hook

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import org.bogdanspbm.pendulum.models.pendulum.Pendulum
import org.bogdanspbm.pendulum.utils.fromHex
import org.bogdanspbm.pendulum.utils.lerpColor
import kotlin.math.cos
import kotlin.math.sin

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

    fun draw(scope: DrawScope, offset: Offset, tick: Long) {

        val spikeCount = 30
        val spikeHeight = 10f
        val spikeAngle = 360f / spikeCount

        val path = Path()
        val center = Offset(
            x + scope.size.width / 2,
            scope.size.height / 2 - y
        ) + offset

        for (i in 0 until spikeCount) {
            val spikeStartX =
                center.x + radius * cos(Math.toRadians(i * spikeAngle.toDouble())).toFloat()
            val spikeStartY =
                center.y + radius * sin(Math.toRadians(i * spikeAngle.toDouble())).toFloat()

            val spikeMidX =
                center.x + (radius + spikeHeight) * cos(Math.toRadians((i * spikeAngle + spikeAngle / 2).toDouble())).toFloat()
            val spikeMidY =
                center.y + (radius + spikeHeight) * sin(Math.toRadians((i * spikeAngle + spikeAngle / 2).toDouble())).toFloat()

            val spikeEndX =
                center.x + radius * cos(Math.toRadians((i * spikeAngle + spikeAngle).toDouble())).toFloat()
            val spikeEndY =
                center.y + radius * sin(Math.toRadians((i * spikeAngle + spikeAngle).toDouble())).toFloat()

            if (i == 0) {
                path.moveTo(spikeStartX, spikeStartY)
            } else {
                path.lineTo(spikeStartX, spikeStartY)
            }

            path.lineTo(spikeMidX, spikeMidY)
            path.lineTo(spikeEndX, spikeEndY)
        }
        path.close()


        scope.drawPath(path, color = Color.fromHex("#585858"), style = Fill)
        scope.drawPath(path, color = Color.fromHex("#373737"), style = Stroke(4f))


        val brush = Brush.radialGradient(
            colors = arrayListOf(
                lerpColor(
                    Color.fromHex("#121212"),
                    Color.fromHex("#FF0000"),
                    (sin(tick.toDouble() / 300).toFloat() + 1) / 2
                ).copy(alpha = 0.8f),
                Color.fromHex("#FF0000").copy(alpha = 0.0f)
            ),
            center = center,
            radius = this.radius
        )

        scope.drawCircle(
            brush = brush,
            center = center,
            radius = this.radius
        )

        scope.drawCircle(
            color = Color.fromHex("#373737"),
            radius = this.radius / 2,
            center = Offset(
                x + scope.size.width / 2,
                scope.size.height / 2 - y
            ) + offset
        )

        scope.drawCircle(
            color = lerpColor(
                Color.fromHex("#121212"),
                Color.fromHex("#FF0000"),
                (sin(tick.toDouble() / 300).toFloat() + 1) / 2
            ),
            radius = this.radius / 2 - 4,
            center = center
        )
    }
}