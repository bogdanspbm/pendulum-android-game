package org.bogdanspbm.pendulum.models.field

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import org.bogdanspbm.pendulum.utils.fromHex

class Background {

    val arrowHeight: Float = 600f
    fun draw(scope: DrawScope, offset: Offset) {


        for (i in -2..5) {
            val point1 = Offset(0f, i * arrowHeight - arrowHeight / 2) + offset
            val point2 =
                Offset(
                    scope.size.width / 2,
                    0.5f * arrowHeight + i * arrowHeight - arrowHeight / 2
                ) + offset
            val point3 = Offset(scope.size.width, i * arrowHeight - arrowHeight / 2) + offset
            val point4 =
                Offset(scope.size.width, arrowHeight + i * arrowHeight - arrowHeight / 2) + offset
            val point5 =
                Offset(
                    scope.size.width / 2,
                    1.5f * arrowHeight + i * arrowHeight - arrowHeight / 2
                ) + offset
            val point6 = Offset(0f, arrowHeight + i * arrowHeight - arrowHeight / 2) + offset


            val path = Path().apply {
                moveTo(point1.x, point1.y)
                lineTo(point2.x, point2.y)
                lineTo(point3.x, point3.y)
                lineTo(point4.x, point4.y)
                lineTo(point5.x, point5.y)
                lineTo(point6.x, point6.y)
                close() // Закрыть путь, чтобы создать параллелограмм
            }

            scope.drawPath(
                path = path,
                color = if (i % 2 == 0) Color.fromHex("#D7A800") else Color.fromHex("#492858")
            )
        }

        for (i in -2..5) {
            val point1 = Offset(0f, i * arrowHeight - arrowHeight / 2) + offset
            val point2 =
                Offset(
                    scope.size.width / 2,
                    0.5f * arrowHeight + i * arrowHeight - arrowHeight / 2
                ) + offset
            val point3 = Offset(scope.size.width, i * arrowHeight - arrowHeight / 2) + offset
            val point4 =
                Offset(scope.size.width, arrowHeight / 4 + i * arrowHeight) + offset
            val point5 =
                Offset(
                    scope.size.width / 2,
                    arrowHeight / 4 + i * arrowHeight + arrowHeight / 2
                ) + offset
            val point6 = Offset(0f, arrowHeight / 4 + i * arrowHeight) + offset


            val path = Path().apply {
                moveTo(point1.x, point1.y)
                lineTo(point2.x, point2.y)
                lineTo(point3.x, point3.y)
                lineTo(point4.x, point4.y)
                lineTo(point5.x, point5.y)
                lineTo(point6.x, point6.y)
                close() // Закрыть путь, чтобы создать параллелограмм
            }

            val gradient = Brush.verticalGradient(
                colors = listOf(Color.Black.copy(alpha = 0.25f), Color.Black.copy(alpha = 0.0f)),
                startY = point1.y,
                endY = point1.y + arrowHeight * 0.75f
            )

            scope.drawPath(
                path = path,
                brush = gradient
            )
        }

    }
}