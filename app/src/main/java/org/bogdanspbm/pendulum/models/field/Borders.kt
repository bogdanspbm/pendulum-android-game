package org.bogdanspbm.pendulum.models.field

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import org.bogdanspbm.pendulum.utils.fromHex

class Borders {
    fun draw(scope: DrawScope, offset: Offset) {
        scope.drawRect(
            color = Color.fromHex("#3C2D55"),
            topLeft = Offset(-60f, -30f) + offset,
            size = Size(120f, scope.size.height + 60)
        )
        scope.drawRect(
            color = Color.fromHex("#51336F"),
            topLeft = Offset(-52f, -30f) + offset,
            size = Size(104f, scope.size.height + 60)
        )

        scope.drawRect(
            color = Color.fromHex("#3C2D55"),
            topLeft = Offset(scope.size.width - 60f, -30f) + offset,
            size = Size(120f, scope.size.height + 60)
        )
        scope.drawRect(
            color = Color.fromHex("#51336F"),
            topLeft = Offset(scope.size.width - 52f, -30f) + offset,
            size = Size(104f, scope.size.height + 60)
        )

        // Shadow
        val brushLeft = Brush.horizontalGradient(
            colors = listOf(Color.Black.copy(alpha = 0.2f), Color.Transparent),
            startX = 60f,
            endX = 150f
        )

        scope.drawRect(
            brush = brushLeft,
            topLeft = Offset(60f, -30f) + offset,
            size = Size(150f, scope.size.height + 60)
        )

        val brushRight = Brush.horizontalGradient(
            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.2f)),
            startX = scope.size.width - 210,
            endX = scope.size.width - 60
        )


        scope.drawRect(
            brush = brushRight,
            topLeft = Offset(scope.size.width - 210, -30f) + offset,
            size = Size(150f, scope.size.height + 60)
        )

    }
}