package org.bogdanspbm.pendulum.views

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import org.bogdanspbm.pendulum.models.game.GameState
import org.bogdanspbm.pendulum.models.pendulum.Pendulum
import org.bogdanspbm.pendulum.ui.theme.PendulumTheme
import java.util.Date

@Composable
fun GameView() {

    val (gameState, setGameState) = remember { mutableStateOf(GameState()) }
    val updateInterval = 10

    LaunchedEffect(Unit) {
        while (true) {
            gameState.tickEvent(updateInterval)
            setGameState(gameState.copy(time = Date().time))
            delay(updateInterval.toLong())
        }
    }

    GameCanvas(game = gameState)
}

@Composable
fun GameCanvas(game: GameState) {
    val pendulum = game.pendulum
    Log.d("ACASD", "HERE")
    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        drawRect(
            color = Color.Black, topLeft = Offset(0f, 0f),
            size = Size(30f, size.height)
        )
        drawRect(
            color = Color.Black, topLeft = Offset(size.width - 30, 0f),
            size = Size(30f, size.height)
        )
        drawCircle(color = Color.Red, radius = pendulum.radius, center = Offset(pendulum.x + 300, pendulum.y + 500))
    })
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PendulumTheme {
        GameView()
    }
}