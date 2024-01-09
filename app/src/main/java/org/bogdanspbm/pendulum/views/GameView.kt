package org.bogdanspbm.pendulum.views

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.bogdanspbm.pendulum.models.game.GameState
import org.bogdanspbm.pendulum.models.pendulum.Pendulum
import org.bogdanspbm.pendulum.ui.theme.PendulumTheme
import java.util.Date

@Composable
fun GameView() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val fieldWidth = configuration.screenWidthDp * density
    val (gameState, setGameState) = remember { mutableStateOf(GameState(fieldWidth = fieldWidth, context = context).prepareGame()) }

    val updateInterval = 5

    LaunchedEffect(Unit) {
        while (true) {
            gameState.tickEvent(updateInterval)
            setGameState(gameState.copy(time = Date().time))
            delay(updateInterval.toLong())
        }
    }

    GameCanvas(game = gameState)

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GameCanvas(game: GameState) {
    val pendulum = game.pendulum
    val textMeasurer = rememberTextMeasurer()


    Box(contentAlignment = Alignment.TopCenter) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        GameState.gameStarted = true
                        GameState.isPointerDown = true
                    }

                    MotionEvent.ACTION_UP -> {
                        GameState.isPointerDown = false
                    }

                    else -> false
                }
                true
            }, onDraw = {
            drawRect(
                color = if (game.isPendulumOutOfField()) Color.Red else Color.Black,
                topLeft = Offset(-30f, -30f) + game.getOffset(),
                size = Size(60f, size.height + 60)
            )
            drawRect(
                color = if (game.isPendulumOutOfField()) Color.Red else Color.Black,
                topLeft = Offset(size.width - 30, -30f) + game.getOffset(),
                size = Size(60f, size.height + 60)
            )

            game.recordItem.drawGameRecord(this, textMeasurer)

            // Line
            drawLine(
                color = Color.Blue,
                start = Offset(
                    pendulum.x + size.width / 2 + pendulum.speedX * 2000,
                    size.height / 2 - pendulum.speedY * 2000
                ) + game.getOffset(),
                end = Offset(
                    pendulum.x + size.width / 2 - pendulum.speedX * 2000,
                    size.height / 2 + pendulum.speedY * 2000
                ) + game.getOffset()
            )

            // Pendulum
            drawCircle(
                color = Color.Red,
                radius = pendulum.radius,
                center = Offset(pendulum.x + size.width / 2, size.height / 2) + game.getOffset()
            )

            pendulum.prevPositions.forEachIndexed { index, trailPosition ->
                val alpha = (index.toFloat() / pendulum.prevPositions.size.toFloat()) / 10
                drawCircle(
                    color = Color.Red.copy(alpha = alpha),
                    radius = pendulum.radius * (alpha * 5 + 0.5f),
                    center = trailPosition + Offset(
                        size.width / 2,
                        size.height / 2 + pendulum.y
                    ) + game.getOffset()
                )
            }


            // Hook
            if (game.attachedHook != null) {
                drawLine(
                    color = Color.Gray,
                    start = Offset(
                        pendulum.x + size.width / 2,
                        size.height / 2
                    ) + game.getOffset(),
                    end = Offset(
                        game.attachedHook!!.x + size.width / 2,
                        size.height / 2 + pendulum.y - game.attachedHook!!.y
                    ) + game.getOffset(),
                    strokeWidth = 4f
                )
            }

            game.hooks.forEach { hook ->
                drawCircle(
                    color = Color.Green,
                    radius = pendulum.radius,
                    center = Offset(
                        hook.x + size.width / 2,
                        size.height / 2 + pendulum.y - hook.y
                    ) + game.getOffset()
                )
            }
        })

        Box(
            Modifier
                .offset(y = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black.copy(alpha = 0.1f))
        ) {
            Text(
                modifier = Modifier.padding(12.dp), text = "${
                    game.score
                }",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        if (!GameState.gameStarted) {
            Box(
                Modifier
                    .offset(y = 600.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Black.copy(alpha = 0.1f))
            ) {
                Text(
                    modifier = Modifier.padding(12.dp), text = "Press to Start",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PendulumTheme {
        GameView()
    }
}