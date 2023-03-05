package ir.ehsan.telegram.free.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import ir.ehsan.telegram.free.composables.AppText

@Composable
@Destination
fun MainScreen() {
    Column(modifier= Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
        AppText(text= "This is main screen")
    }
}