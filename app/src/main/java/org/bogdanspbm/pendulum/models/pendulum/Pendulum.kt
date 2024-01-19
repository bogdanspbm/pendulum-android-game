package org.bogdanspbm.pendulum.models.pendulum

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import org.bogdanspbm.pendulum.utils.fromHex

data class Pendulum(
    var x: Float = 0f,
    var y: Float = 0f,
    var speedY: Float = 1f,
    var speedX: Float = 0f,
    var angle: Double = 0.toDouble(),
    var rotationDirection: Int = 1,
    var radius: Float = 40f,
    var prevPositions: MutableList<Offset> = mutableListOf()
) {

    fun refresh(){
        x = 0f
        y = 0f
        speedY = 1f
        speedX = 0f
        rotationDirection = 1
    }
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

    fun draw(scope: DrawScope, offset: Offset){

        prevPositions.forEachIndexed { index, trailPosition ->
            val alpha = (index.toFloat() / prevPositions.size.toFloat()) / 10
            scope.drawCircle(
                color = Color.fromHex("#FFC700").copy(alpha = alpha),
                radius = radius * (alpha * 5 + 0.5f),
                center = trailPosition + Offset(
                    scope.size.width / 2,
                    scope.size.height / 2 + y
                ) + offset
            )
        }

        scope.drawCircle(
            color = Color.fromHex("#373737"),
            radius = this.radius,
            center = Offset(x + scope.size.width / 2, scope.size.height / 2) + offset
        )

        scope.drawCircle(
            color = Color.fromHex("#585858"),
            radius = this.radius - 4,
            center = Offset(x + scope.size.width / 2, scope.size.height / 2) + offset
        )

        scope.drawCircle(
            color = Color.fromHex("#373737"),
            radius = this.radius / 2,
            center = Offset(x + scope.size.width / 2, scope.size.height / 2) + offset
        )

        scope.drawCircle(
            color =  Color.fromHex("#FFC700"),
            radius = this.radius / 2 - 4,
            center = Offset(x + scope.size.width / 2, scope.size.height / 2) + offset
        )

    }



}