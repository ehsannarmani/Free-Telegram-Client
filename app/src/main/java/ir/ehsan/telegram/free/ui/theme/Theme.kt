package ir.ehsan.telegram.free.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

val forceDark = true

private val DarkColorPalette = darkColors(
    surface = Black200,
    primary = Black500,
    background = Black200,
    primaryVariant = Black700,
    secondary = Green,
)

private val LightColorPalette = lightColors(
    surface = White,
    primary = Green,
    background = White,
    primaryVariant = GreenDark,
    secondary = Green,

)

@Composable
fun FreeTelegramTheme(darkTheme: Boolean = isSystemInDarkTheme() || forceDark, content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val customColors = if(darkTheme){
        customDarkColors
    }else{
        customLightColors
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        CompositionLocalProvider(LocalCustomColors provides customColors) {
            MaterialTheme(
                colors = colors,
                typography = Typography,
                shapes = Shapes,
                content = content
            )
        }
    }
}