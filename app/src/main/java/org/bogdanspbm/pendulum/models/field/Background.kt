package org.bogdanspbm.pendulum.models.field

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.imageResource
import org.bogdanspbm.pendulum.utils.fromHex
import org.bogdanspbm.pendulum.utils.rotateOffset

class Background {

    val arrowHeight = 600f
    fun draw(scope: DrawScope, offset: Offset) {
        for (i in 0..5) {
            val point1 = Offset(0f, i * arrowHeight - arrowHeight / 2)
            val point2 =
                Offset(scope.size.width / 2, 0.5f * arrowHeight + i * arrowHeight - arrowHeight / 2)
            val point3 = Offset(scope.size.width, i * arrowHeight - arrowHeight / 2)
            val point4 = Offset(scope.size.width, arrowHeight + i * arrowHeight - arrowHeight / 2)
            val point5 =
                Offset(scope.size.width / 2, 1.5f * arrowHeight + i * arrowHeight - arrowHeight / 2)
            val point6 = Offset(0f, arrowHeight + i * arrowHeight - arrowHeight / 2)

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
                color = if (i % 2 == 0) Color.fromHex("#51336F") else Color.fromHex("#D7A800")
            )
        }

    }
}