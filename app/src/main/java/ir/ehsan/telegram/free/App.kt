package ir.ehsan.telegram.free

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import ir.ehsan.telegram.free.composables.AppText
import ir.ehsan.telegram.free.composables.defaultTextStyle
import ir.ehsan.telegram.free.screens.NavGraphs
import ir.ehsan.telegram.free.screens.auth.AuthViewModel
import ir.ehsan.telegram.free.screens.destinations.AuthScreenDestination
import ir.ehsan.telegram.free.screens.destinations.MainScreenDestination
import ir.ehsan.telegram.free.ui.theme.FreeTelegramTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun App() {
    FreeTelegramTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val appState = rememberAppState()
            val authViewModel:AuthViewModel = viewModel()

            Scaffold(
                topBar = {
                    AnimatedVisibility(
                        visible = appState.shouldShowAppBar,
                        enter = slideInVertically() + fadeIn(),
                        exit = slideOutVertically() + fadeOut()
                    ) {
                        TopAppBar(
                            backgroundColor = MaterialTheme.colors.primary,
                            title = {
                                AppText(appState.getAppBarTitle, style = defaultTextStyle.copy(fontSize = 15.sp, fontWeight = FontWeight.Bold))
                            }
                        )
                    }
                }
            ) {
                Box(modifier= Modifier
                    .fillMaxSize()
                    .animateContentSize(animationSpec = tween(200))){
                    DestinationsNavHost(
                        navController = appState.navController,
                        navGraph = NavGraphs.root,
                        startRoute = if(authViewModel.userLoggedIn) MainScreenDestination else AuthScreenDestination,
                    )
                }
            }
        }
    }
}
