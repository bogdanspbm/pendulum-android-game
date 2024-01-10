package org.bogdanspbm.pendulum.models.field

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.bogdanspbm.pendulum.shapes.ArrowShape
import org.bogdanspbm.pendulum.ui.theme.PendulumTheme
import org.bogdanspbm.pendulum.utils.fromHex


@Composable
fun GameBackground(modifier: Modifier = Modifier, offset: Offset = Offset(0f, 0f)) {
    Box(modifier = modifier.background(Color.Black)) {
        for (i in 0..5) {
            val color = if(i % 2 ==0 ) Color.fromHex("#D7A800") else Color.fromHex("#51336F")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (200 * (5-i)).dp - 300.dp )
                    .shadow(elevation = (8 * i).dp, shape = ArrowShape(200f))
                    .clip(ArrowShape(200f))
                    .height(300.dp)
                    .background(color)

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PendulumTheme {
        GameBackground(modifier = Modifier.fillMaxSize())
    }
}