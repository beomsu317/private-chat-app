package com.beomsu317.privatechatapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

//private val DarkColorPalette = darkColors(
//    primary = Purple200,
//    primaryVariant = Purple700,
//    secondary = Teal200
//)

private val LightColorPalette = lightColors(
    primary = Color(0xff4b5ad1),
    primaryVariant = Color(0xff00319f),
    onPrimary = Color(0xffffffff),
//    secondary = Color(0xff4b9dd0),
//    onSecondary = Color(0xff000000),
    background = Color.White,
    surface = Color.White,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun PrivateChatAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
//    val colors = if (darkTheme) {
//        DarkColorPalette
//    } else {
//        LightColorPalette
//    }

    val colors = LightColorPalette

    val systemUiController = rememberSystemUiController()
    systemUiController.isStatusBarVisible = false

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}