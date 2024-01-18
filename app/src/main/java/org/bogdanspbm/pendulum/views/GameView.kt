package org.bogdanspbm.pendulum.views

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.bogdanspbm.pendulum.models.field.Background
import org.bogdanspbm.pendulum.models.field.Borders
import org.bogdanspbm.pendulum.models.game.GameState
import org.bogdanspbm.pendulum.ui.theme.BungeeTypography
import org.bogdanspbm.pendulum.ui.theme.PendulumTheme
import java.util.Date
import kotlin.math.abs

@Composable
fun GameView() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val fieldWidth = configuration.screenWidthDp * density
    val (gameState, setGameState) = remember {
        mutableStateOf(
            GameState(
                fieldWidth = fieldWidth,
                context = context
            ).prepareGame()
        )
    }

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
    val borders = Borders()
    val background = Background()


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


            game.recordItem.draw(this, textMeasurer)
            background.draw(this, game.getOffset() + Offset(0f, pendulum.y % 1200))
            borders.draw(this, game.getOffset())

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
            pendulum.draw(this, game.getOffset())


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

            for (hook in game.hooks) {
                if (abs((hook.y - pendulum.y + (hook.radius - pendulum.radius))) > size.height / 2) {
                    continue
                }

                hook.draw(this, game.getOffset() + Offset(0f, game.pendulum.y), game.tick)
            }


        })

        Column(
            modifier= Modifier.fillMaxWidth().offset(x = 32.dp, y = -24.dp),
            horizontalAlignment = Alignment.Start)
         {
            Text(text = "${game.score}",
                style = BungeeTypography.titleLarge,
                color = Color.White
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