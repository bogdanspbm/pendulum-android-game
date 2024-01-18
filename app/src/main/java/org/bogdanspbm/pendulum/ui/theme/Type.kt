package org.bogdanspbm.pendulum.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.bogdanspbm.pendulum.R

val BungeeFontFamily = FontFamily(Font(R.font.bungee)) // Replace "custom_font" with the actual font resource name

// Set of Material typography styles to start with
val BungeeTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = BungeeFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = BungeeFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
        lineHeight = 56.sp,
        letterSpacing = 0.sp
    ),
    titleMedium =TextStyle(
        fontFamily = BungeeFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp
    ) ,
    labelSmall = TextStyle(
        fontFamily = BungeeFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)