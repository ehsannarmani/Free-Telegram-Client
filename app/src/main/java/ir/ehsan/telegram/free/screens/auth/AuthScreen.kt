package ir.ehsan.telegram.free.screens.auth

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import ir.ehsan.telegram.free.composables.AppText
import ir.ehsan.telegram.free.composables.SimpleActionBarLayout
import ir.ehsan.telegram.free.composables.Spacer
import ir.ehsan.telegram.free.composables.State
import ir.ehsan.telegram.free.utils.mergePhoneNumber
import ir.ehsan.telegram.free.utils.remember
import kotlinx.coroutines.flow.collect

@RootNavGraph(start = true)
@Destination()
@Composable
fun AuthScreen(
    navigator: DestinationsNavigator,
    authViewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current

    authViewModel.init(
        onLoggedIn = {

        },
        onStepChanged = {
            Log.e("tag","step changed to : ${it.name}")
        },
        onErrorThrown = {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    )
    val socketConnected by authViewModel.socketConnected.remember().collectAsState(initial = false)
    val authStep by authViewModel.step.remember().collectAsState(initial = -1)
    var phoneNumber by State("")
    var phonePrefix by State("+98")
    var stepName = "شماره موبایل"


    SimpleActionBarLayout(
        title = if (socketConnected) stepName else "درحال اتصال..."
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center
        ) {

            AppText(text = "شماره موبایل خود را وارد کنید:")
            Spacer.SmallSpacer()
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                    },
                    leadingIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Spacer.LargeSpacer(horizontal = true)
                            Text("+98")
                            Spacer.LargeSpacer(horizontal = true)
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(40.dp)
                                    .background(Color.White)
                            )
                            Spacer.LargeSpacer(horizontal = true)

                        }
                    },
                    placeholder = {
                        Text("000-000-0000")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
            }
            Spacer.LargeSpacer()
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary
                ),
                onClick = {
                    authViewModel.sendPhoneNumber(mergePhoneNumber(
                        code = phonePrefix,
                        phoneNumber = phoneNumber
                    ))
                }) {
                AppText("ادامه")
            }
        }
    }
}