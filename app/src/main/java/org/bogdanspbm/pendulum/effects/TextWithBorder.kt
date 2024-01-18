import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import org.bogdanspbm.pendulum.R
import org.bogdanspbm.pendulum.ui.theme.BungeeFontFamily
import org.bogdanspbm.pendulum.ui.theme.PendulumTheme

@Composable
fun OutlinedText(
    modifier: Modifier = Modifier,
    textColor: Int = Color.WHITE,
    outlineColor: Int = Color.BLACK,
    text: String = "",
    alignment: android.graphics.Paint.Align? = android.graphics.Paint.Align.LEFT,
    textStrokeWidth: Float = 18f,
    fontSize: Float = 100f
) {

    val textPaintStroke = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.STROKE
        textSize = fontSize
        color = outlineColor
        strokeWidth = textStrokeWidth
        strokeMiter = 10f
        textAlign = alignment
        typeface = ResourcesCompat.getFont(LocalContext.current, R.font.bungee)
        strokeJoin = android.graphics.Paint.Join.ROUND
    }

    // Create a Paint that has white fill
    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.FILL
        textSize = fontSize
        color = textColor
        textAlign = alignment
        typeface = ResourcesCompat.getFont(LocalContext.current, R.font.bungee)
    }

    Canvas(
        modifier = modifier,
        onDraw = {
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    text,
                    if (alignment != android.graphics.Paint.Align.CENTER) 0f else size.width / 2,
                    fontSize,
                    textPaintStroke
                )

                it.nativeCanvas.drawText(
                    text,
                    if (alignment != android.graphics.Paint.Align.CENTER) 0f else size.width / 2,
                    fontSize,
                    textPaint
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PendulumTheme {
        OutlinedText(
            modifier = Modifier.size(600.dp, 80.dp),
            alignment = android.graphics.Paint.Align.CENTER,
            text = "Hello, World!"
        )
    }
}