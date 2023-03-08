package ir.ehsan.telegram.free

import android.util.Log
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import ir.ehsan.telegram.free.screens.destinations.AuthScreenDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow


class AppState(
    var navController: NavHostController? = null,
) {
    private val appBarDestinations = MutableStateFlow(
        listOf(
            NavigationDestination(destination = AuthScreenDestination, title = "ورود")
        )
    )


    val shouldShowAppBar: Boolean
        @Composable
        get() =
            navController?.currentDestinationAsState()?.value in appBarDestinations.collectAsState().value.map { it.destination }

    val getAppBarTitle: String
        @Composable
        get() =
            appBarDestinations.collectAsState().value.find { it.destination == navController?.currentDestinationAsState()?.value }?.title ?: ""

    fun updateAppBarDestinations(block:(List<NavigationDestination>)->List<NavigationDestination>){
        appBarDestinations.value = block(appBarDestinations.value)
    }
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