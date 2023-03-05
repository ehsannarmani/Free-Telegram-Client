package ir.ehsan.telegram.free.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import ir.ehsan.telegram.free.ui.theme.customColors
import ir.ehsan.telegram.free.ui.theme.dana


val defaultTextStyle:TextStyle @Composable get() = TextStyle(
    color = MaterialTheme.customColors.textColor,
    fontFamily = dana
)

@Composable
fun AppText(
    text:String,
    style: TextStyle = defaultTextStyle
) {
    Text(text = text, style = style)
}