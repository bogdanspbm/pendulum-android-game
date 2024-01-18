import android.graphics.Typeface
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import org.bogdanspbm.pendulum.R
import org.bogdanspbm.pendulum.ui.theme.BungeeFontFamily
import org.bogdanspbm.pendulum.ui.theme.PendulumTheme

@Composable
fun OutlinedText(text: String = ""){

    val textPaintStroke = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.STROKE
        textSize = 100f
        color = android.graphics.Color.BLACK
        strokeWidth = 12f
        strokeMiter= 10f
        typeface = ResourcesCompat.getFont(LocalContext.current, R.font.bungee)
        strokeJoin = android.graphics.Paint.Join.ROUND
    }

    // Create a Paint that has white fill
    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.FILL
        textSize = 100f
        color = android.graphics.Color.WHITE
        typeface = ResourcesCompat.getFont(LocalContext.current, R.font.bungee)
    }

    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    text,
                    200f,
                    200.dp.toPx(),
                    textPaintStroke
                )

                it.nativeCanvas.drawText(
                    text,
                    200f,
                    200.dp.toPx(),
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
        OutlinedText(text = "Hello, World!")
    }
}