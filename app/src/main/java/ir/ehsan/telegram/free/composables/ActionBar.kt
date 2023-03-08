package ir.ehsan.telegram.free.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleActionBar(
    title:String
) {
    Box(modifier= Modifier
        .fillMaxWidth()
        .height(56.dp)
        .background(MaterialTheme.colors.primary),
    contentAlignment = Alignment.Center){
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            AppText(title, style = defaultTextStyle.copy(fontSize = 16.sp))
        }
    }
}

@Composable
fun SimpleActionBarLayout(
    title: String,
    content: @Composable ()->Unit
) {
    Column(modifier=Modifier.fillMaxSize()) {
        SimpleActionBar(title = title)
        content()
    }
}