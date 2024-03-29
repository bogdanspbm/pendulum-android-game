package org.bogdanspbm.pendulum.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.bogdanspbm.pendulum.ui.theme.PendulumTheme


@Composable
fun GameOverView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black.copy(alpha = 0.05f))
            .padding(16.dp)
    ) {
        Column {
            MenuButton(text = "Try Again")
            Box(modifier = Modifier.height(12.dp))
            MenuButton(text = "Continue")
            Box(modifier = Modifier.height(12.dp))
            MenuButton(text = "Back to Menu")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GameOverViewPreview() {
    PendulumTheme {
        GameOverView(modifier = Modifier.size(400.dp, 300.dp))
    }
}