package org.bogdanspbm.pendulum.views

import OutlinedText
import android.graphics.Paint
import android.media.MediaPlayer
import android.net.Uri
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import org.bogdanspbm.pendulum.R
import org.bogdanspbm.pendulum.enums.ENavigation
import org.bogdanspbm.pendulum.models.field.Background
import org.bogdanspbm.pendulum.models.field.Borders
import org.bogdanspbm.pendulum.models.game.GameState
import org.bogdanspbm.pendulum.ui.theme.PendulumTheme
import org.bogdanspbm.pendulum.utils.fromHex
import org.bogdanspbm.pendulum.utils.getGameRecord
import java.util.Date
import kotlin.math.abs

@Composable
fun GameView(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),mediaPlayer: MediaPlayer  = MediaPlayer()
) {
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

    GameCanvas(game = gameState, navController = navController, mediaPlayer = mediaPlayer)

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GameCanvas(game: GameState, navController: NavController = rememberNavController(),mediaPlayer: MediaPlayer = MediaPlayer()) {
    val pendulum = game.pendulum
    val context = LocalContext.current
    val gameRecord = getGameRecord(context!!)
    val textMeasurer = rememberTextMeasurer()
    val borders = Borders()
    val background = Background()

    val buttonPlayer = remember { MediaPlayer.create(context, R.raw.button_click) }

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


            background.draw(this, game.getOffset() + Offset(0f, pendulum.y % 1200))
            game.recordItem.draw(this, textMeasurer)
            borders.draw(this, game.getOffset())
            

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
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        )
        {
            OutlinedText(
                modifier = Modifier
                    .offset(x = 32.dp)
                    .height(48.dp)
                    .width(100.dp),
                text = "${game.score}"
            )
            OutlinedText(
                modifier = Modifier
                    .height(48.dp)
                    .width(100.dp)
                    .offset(x = 32.dp, y = -6.dp),
                fontSize = 50f,
                textColor = Color.fromHex("#FFC700").toArgb(),
                text = "${gameRecord}"
            )
        }

        if (!GameState.gameStarted) {
            Box(
                Modifier
                    .fillMaxWidth(1f)
                    .height(64.dp)
                    .offset(y = 600.dp)
                    .background(Color.Black.copy(alpha = 0.1f))
            ) {
                OutlinedText(
                    modifier = Modifier
                        .offset(y = 8.dp)
                        .fillMaxSize(),
                    text = "Press To Start",
                    alignment = Paint.Align.CENTER
                )
            }
        }
        if (GameState.isCollided) {
            Column(
                modifier = Modifier
                    .offset(y = 300.dp)
                    .fillMaxWidth(0.8f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.fromHex("#9256A7"))
                    .padding(20.dp)
            ) {
                MenuButton(text = "Restart", onClick = {
                    game.prepareGame()
                    buttonPlayer.start()
                    buttonPlayer.setOnCompletionListener { mp -> mp.release()}
                    GameState.gameStarted = false
                    GameState.isPointerDown = false
                    GameState.isCollided = false
                    navController.navigate(ENavigation.GAME.name)
                })
                Box(modifier = Modifier.height(20.dp))
                MenuButton(text = "Menu", onClick = {
                    mediaPlayer.stop()
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(context, Uri.parse("android.resource://${context.packageName}/${R.raw.menu}"))
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                    buttonPlayer.start()
                    buttonPlayer.setOnCompletionListener { mp -> mp.release()}
                    navController.navigate(ENavigation.MENU.name)
                })
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