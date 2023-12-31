package org.bogdanspbm.pendulum.models.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.sp

class GameRecord(val parent: GameState, val record: Int) {

    fun getHeight(): Float {
        return 200f
    }

    fun getPositionY(): Float {
        return (-record.toFloat() * 50 - getHeight()) + parent.pendulum.y
    }

    fun drawGameRecord(scope: DrawScope, textMeasurer: TextMeasurer) {

        scope.drawRect(
            color = Color.Black.copy(alpha = 0.05f),
            topLeft = Offset(-30f, scope.size.height / 2 + getPositionY()) + parent.getOffset(),
            size = Size(scope.size.width + 60f, getHeight())
        )

        val measuredText =
            textMeasurer.measure(
                AnnotatedString(record.toString()),
                style = TextStyle(fontSize = 64.sp)
            )

        scope.drawText(
            textLayoutResult = measuredText,
            topLeft = Offset(
                scope.size.width / 2 - measuredText.size.width / 2,
                scope.size.height / 2 + getPositionY()  + getHeight() / 2- measuredText.size.height / 2
            ),
        )
    }
}