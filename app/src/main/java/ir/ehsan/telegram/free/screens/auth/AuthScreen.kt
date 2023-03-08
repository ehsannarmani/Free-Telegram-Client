package ir.ehsan.telegram.free.screens.auth

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
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

@RootNavGraph(start = true)
@Destination()
@Composable
fun AuthScreen(
    navigator: DestinationsNavigator
) {

    var socketConnected by State(false)
    var phoneNumber by State("")
    var stepName = "شماره موبایل"


    var socket: Socket? = null

    socket = IO.socket("http://192.168.1.4:3000")

    socket?.on(Socket.EVENT_CONNECT){
        Log.e("tag","on connection")
        socketConnected = true
    }
    socket?.on(Socket.EVENT_DISCONNECT){
        Log.e("tag","on disconnect")
        socketConnected = false
    }
    socket?.on(Socket.EVENT_CONNECT_ERROR){
        Log.e("tag","on connect error: ${it[0]}")
    }

    socket?.connect()

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
                    Log.e("connected",    socket?.connected().toString())
                    socket?.emit("authPhoneNumber","09146478614")

                }) {
                AppText("ادامه")
            }
        }
    }
}