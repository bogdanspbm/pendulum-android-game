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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.bogdanspbm.pendulum.shapes.ArrowShape
import org.bogdanspbm.pendulum.ui.theme.PendulumTheme
import org.bogdanspbm.pendulum.utils.fromHex
import org.bogdanspbm.pendulum.utils.pxToDp


@Composable
fun GameBackground(modifier: Modifier = Modifier, offset: Offset = Offset(0f, 0f)) {
    val context = LocalContext.current
    val height = 400;

    Box(modifier = modifier.background(Color.Black)) {
        for (i in 0..3) {
            val offsetY = pxToDp(context, offset.y).toInt()
            val color = if ((i + offsetY / height) % 2 == 0) Color.fromHex("#D7A800") else Color.fromHex("#51336F")
            ArrowComponent(
                modifier = Modifier.offset(
                    x = offset.x.dp,
                    y = (height * (3 - i)).dp - 500.dp + (offsetY % height).dp
                ), color = color
            )
            /* Box(
                 modifier = Modifier
                     .fillMaxWidth()
                     .offset(
                         x = offset.x.dp,
                         y = (300 * (3 - i)).dp - 800.dp + (pxToDp(
                             context,
                             offset.y
                         ).toInt() % 300).dp
                     )
                     .shadow(elevation = (8 * i).dp, shape = ArrowShape(200f))
                     .clip(ArrowShape(200f))
                     .height(400.dp)
                     .background(color)

             )*/
        }
    }
}

@Composable
fun ArrowComponent(modifier: Modifier = Modifier, color: Color) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = (8).dp, shape = ArrowShape(200f))
            .clip(ArrowShape(200f))
            .height(500.dp)
            .background(color)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PendulumTheme {
        GameBackground(modifier = Modifier.fillMaxSize())
    }
}