package org.bogdanspbm.pendulum.views

import OutlinedText
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.bogdanspbm.pendulum.enums.ENavigation
import org.bogdanspbm.pendulum.models.field.Background
import org.bogdanspbm.pendulum.models.game.GameState
import org.bogdanspbm.pendulum.ui.theme.BungeeFontFamily
import org.bogdanspbm.pendulum.ui.theme.BungeeTypography
import org.bogdanspbm.pendulum.ui.theme.PendulumTheme
import org.bogdanspbm.pendulum.utils.fromHex

@Composable
fun MainMenuView(modifier: Modifier = Modifier, navController: NavController = rememberNavController()) {
    val background = Background()
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
            background.draw(this, Offset(0f, 0f))
        })
        Box(
            Modifier
                .fillMaxWidth(1f)
                .height(64.dp)
                .offset(y = -365.dp)
        ) {
            OutlinedText(
                modifier = Modifier
                    .offset(y = 8.dp)
                    .fillMaxSize(),
                text = "Pendulum",
                textStrokeWidth = 12f,
                textColor = Color.fromHex("#492858").toArgb(),
                outlineColor = Color.fromHex("#492858").copy(alpha = 0.2f).toArgb(),
                fontSize = 150f,
                alignment = Paint.Align.CENTER
            )
        }

        Column(
            modifier = Modifier
                .offset(y = 20.dp)
                .fillMaxWidth(0.8f)
                .wrapContentHeight()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.fromHex("#9256A7"))
                .padding(20.dp)
        ) {
            MenuButton(text = "Start", onClick = {
                GameState.gameStarted = false
                GameState.isPointerDown = false
                GameState.isCollided = false
                navController.navigate(ENavigation.GAME.name)
            })
            Box(modifier = Modifier.height(20.dp))
            MenuButton(text = "Shop", onClick = {
                navController.navigate(ENavigation.SHOP.name)
            })
            Box(modifier = Modifier.height(20.dp))
            MenuButton(text = "Settings", onClick = {
                navController.navigate(ENavigation.SETTINGS.name)
            })
            Box(modifier = Modifier.height(20.dp))
            MenuButton(text = "Rating", onClick = {
                navController.navigate(ENavigation.RATING.name)
            })
        }
    }
}

@Composable
fun MenuButton(modifier: Modifier = Modifier, text: String = "Button", onClick: () -> Unit = {}) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .height(64.dp)
            .fillMaxWidth()
            .background(Color.fromHex("#51336F"))
            .clickable { onClick() }
    ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 1.dp),
            text = text,
            style = BungeeTypography.titleMedium,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MenuButtonPreview() {
    PendulumTheme {
        MenuButton()
    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuPreview() {
    PendulumTheme {
        MainMenuView()
    }
}