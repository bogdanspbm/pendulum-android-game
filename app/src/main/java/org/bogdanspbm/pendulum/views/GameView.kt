package org.bogdanspbm.pendulum.views

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import org.bogdanspbm.pendulum.models.game.GameState
import org.bogdanspbm.pendulum.models.pendulum.Pendulum
import org.bogdanspbm.pendulum.ui.theme.PendulumTheme
import java.util.Date

@Composable
fun GameView() {
    val (gameState, setGameState) = remember { mutableStateOf(GameState().prepareGame()) }

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
    Canvas(modifier = Modifier
        .fillMaxSize()
        .pointerInteropFilter {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
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
            color = Color.Black, topLeft = Offset(0f, 0f),
            size = Size(30f, size.height)
        )
        drawRect(
            color = Color.Black, topLeft = Offset(size.width - 30, 0f),
            size = Size(30f, size.height)
        )

        drawLine(
            color = Color.Blue,
            start = Offset(
                pendulum.x + size.width / 2 + pendulum.speedX * 2000,
                size.height / 2 - pendulum.speedY * 2000
            ),
            end = Offset(
                pendulum.x + size.width / 2 - pendulum.speedX * 2000,
                size.height / 2 + pendulum.speedY * 2000
            )
        )

        // Pendulum
        drawCircle(
            color = Color.Red,
            radius = pendulum.radius,
            center = Offset(pendulum.x + size.width / 2, size.height / 2)
        )

        pendulum.prevPositions.forEachIndexed { index, trailPosition ->
            val alpha = (index.toFloat() / pendulum.prevPositions.size.toFloat()) / 10
            drawCircle(
                color = Color.Red.copy(alpha = alpha),
                radius = pendulum.radius,
                center = trailPosition + Offset(size.width / 2, size.height / 2 + pendulum.y)
            )
        }

        if (game.attachedHook != null) {
            drawLine(
                color = Color.Gray,
                start = Offset(pendulum.x + size.width / 2, size.height / 2),
                end = Offset(
                    game.attachedHook!!.x + size.width / 2,
                    size.height / 2 + pendulum.y - game.attachedHook!!.y
                ),
                strokeWidth = 4f
            )
        }

        game.hooks.forEach { hook ->
            drawCircle(
                color = Color.Green,
                radius = pendulum.radius,
                center = Offset(hook.x + size.width / 2, size.height / 2 + pendulum.y - hook.y)
            )
        }
    })
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PendulumTheme {
        GameView()
    }
}