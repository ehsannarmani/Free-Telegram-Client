package ir.ehsan.telegram.free

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import ir.ehsan.telegram.free.screens.destinations.AuthScreenDestination
import ir.ehsan.telegram.free.screens.destinations.MainScreenDestination

class AppState(
    val navController: NavHostController,
) {
    private val appBarDestinations = listOf(
        NavigationDestination(destination = AuthScreenDestination, title = "احرازهویت")
    )


    val shouldShowAppBar: Boolean
        @Composable
        get() =
            navController.currentDestinationAsState().value in appBarDestinations.map { it.destination }

    val getAppBarTitle: String
        @Composable
        get() =
            appBarDestinations.find { it.destination == navController.currentDestinationAsState().value }?.title ?: ""

}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
): AppState {
    return remember {
        AppState(
            navController = navController,
        )
    }
}

data class NavigationDestination(
    val destination: DestinationSpec<*>,
    val title: String
)