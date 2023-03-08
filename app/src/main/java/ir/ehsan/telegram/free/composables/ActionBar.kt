package ir.ehsan.telegram.free.composables

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SimpleActionBar(
    title: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedContent(targetState = title, transitionSpec = {
                (scaleIn() + fadeIn() with scaleOut() + fadeOut())
                    .using(
                        SizeTransform(clip = false)
                    )
            }) {
                AppText(title, style = defaultTextStyle.copy(fontSize = 16.sp))
            }

        }
    }
}

@Composable
fun SimpleActionBarLayout(
    title: String,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SimpleActionBar(title = title)
        content()
    }
}