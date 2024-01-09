package org.bogdanspbm.pendulum.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
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

@Composable
fun MenuButton(text: String = "Press", modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .height(42.dp)
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.05f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .offset(y = -2.dp)
                .padding(0.dp), text = text,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameOverViewPreview() {
    PendulumTheme {
        GameOverView(modifier = Modifier.size(400.dp, 300.dp))
    }
}