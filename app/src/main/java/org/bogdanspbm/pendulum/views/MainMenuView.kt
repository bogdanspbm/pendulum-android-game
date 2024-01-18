package org.bogdanspbm.pendulum.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import org.bogdanspbm.pendulum.models.field.Background
import org.bogdanspbm.pendulum.ui.theme.PendulumTheme

@Composable
fun MainMenuView() {
    val background = Background()
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
            background.draw(this, Offset(0f, 0f))
        })
    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuPreview() {
    PendulumTheme {
        MainMenuView()
    }
}