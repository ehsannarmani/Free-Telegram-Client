package ir.ehsan.telegram.free.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalCustomColors = staticCompositionLocalOf { customLightColors }

val customLightColors = CustomColors(
    textColor = Color.Black
)

val customDarkColors = CustomColors(
    textColor = Color.White
)

val MaterialTheme.customColors: CustomColors
    @Composable
    get() = LocalCustomColors.current

@Immutable
data class CustomColors(
    val textColor: Color
)