package ir.ehsan.telegram.free.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ir.ehsan.telegram.free.composables.AppText
import ir.ehsan.telegram.free.screens.destinations.MainScreenDestination

@RootNavGraph(start = true)
@Destination()
@Composable
fun AuthScreen(
    navigator:DestinationsNavigator
) {
    Column(modifier=Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally){
        AppText("This is auth screen!")
        Box(modifier= Modifier.height(10.dp))
        Button(
            onClick = {
                navigator.navigate(MainScreenDestination)
            }
        ){
            AppText("Go to main screen")
        }
    }
}