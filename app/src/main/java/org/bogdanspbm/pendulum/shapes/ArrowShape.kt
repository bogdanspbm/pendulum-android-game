package org.bogdanspbm.pendulum.shapes

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection


class ArrowShape(val depth: Float = 0f) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            Path().apply {
                moveTo(0f, size.height - depth)
                lineTo(x = size.width / 2, y = size.height)
                lineTo(x = size.width, y = size.height - depth)
                lineTo(x = size.width, y = 0f)
                lineTo(x = size.width / 2, y = depth)
                lineTo(x = 0f, y = 0f)
            }
        )
    }
}
