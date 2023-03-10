package ir.ehsan.telegram.free.composables

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
    loadingContent: @Composable AnimatedVisibilityScope.() -> Unit = {
        Box(modifier = Modifier.size(30.dp)) {
            CircularProgressIndicator(strokeWidth = 2.dp, color = Color.White)
        }
    },
    loading: Boolean = false,
    animations: Pair<EnterTransition, ExitTransition> = scaleIn() to scaleOut(),
    onTap: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .animateContentSize()
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colors.secondary)
            .height(40.dp)
            .padding(horizontal = 16.dp)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
                onClick = onTap
            ), contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = loading,
            content = loadingContent,
            enter = animations.first,
            exit = animations.second
        )
        AnimatedVisibility(
            visible = !loading,
            content = content,
            enter = animations.first,
            exit = animations.second
        )
    }
}