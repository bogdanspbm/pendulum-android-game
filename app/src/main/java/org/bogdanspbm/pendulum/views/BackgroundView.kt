package org.bogdanspbm.pendulum.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.bogdanspbm.pendulum.R
import org.bogdanspbm.pendulum.shapes.ArrowShape
import org.bogdanspbm.pendulum.ui.theme.PendulumTheme
import org.bogdanspbm.pendulum.utils.fromHex
import org.bogdanspbm.pendulum.utils.pxToDp


@Composable
fun GameBackground(modifier: Modifier = Modifier, offset: Offset = Offset(0f, 0f)) {
    Image(
        modifier = modifier.offset(y = offset.y.dp),
        painter = painterResource(id = R.drawable.ic_background),
        contentDescription = "",
        contentScale = ContentScale.FillWidth
    )
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
fun BackgroundPreview() {
    PendulumTheme {
        GameBackground(modifier = Modifier.fillMaxSize())
    }
}