package ir.ehsan.telegram.free.screens.auth

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.beust.klaxon.Klaxon
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import ir.ehsan.telegram.free.composables.*
import ir.ehsan.telegram.free.composables.otp.Otp
import ir.ehsan.telegram.free.screens.auth.models.AuthData
import ir.ehsan.telegram.free.utils.mergePhoneNumber
import ir.ehsan.telegram.free.utils.remember
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@RootNavGraph(start = true)
@Destination()
@Composable
fun AuthScreen(
    navigator: DestinationsNavigator,
    authViewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val socketConnected by authViewModel.socketConnected.remember().collectAsState(initial = false)
    var loading by State(false)
    val authStep by authViewModel.step.remember().collectAsState(-1)


    var stepName by State("ورود")

    var phoneNumber by State("")
    var phonePrefix by State("+98")
    var phoneCodeError by State(false)
    var loginSession by State<String?>(null)

    val phone = mergePhoneNumber(code = phonePrefix, phoneNumber = phoneNumber)

    LaunchedEffect(Unit) {
        authViewModel.init(
            onLoggedIn = { session ->
                loading = false
                Log.e("tag", "you are logged in: $session")
                loginSession = session
            },
            onStepChanged = {
                loading = false
                Log.e("tag", "step changed to : ${it.name}")
            },
            onErrorThrown = {
                loading = false
                Log.e("tag", "Error thrown: ${it.code}")
                var errorMessage: String = ""
                when (it.code) {
                    420 -> {
                        errorMessage = "بیش از حد تلاش کرده اید، بعدا امتحان کنید."
                    }
                    400 -> {
                        errorMessage = "کد وارد شده نادرست است."
                        scope.launch {
                            phoneCodeError = true
                            delay(1000)
                            phoneCodeError = false
                        }
                    }
                }
                scope.launch(Dispatchers.Main) {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    SimpleActionBarLayout(
        title = if (socketConnected) stepName else "درحال اتصال..."
    ) {
        Column(){
            AnimatedContent(targetState = authStep) {
                when (authStep) {
                    -1 -> {

                        PhoneNumberStep(
                            vm = authViewModel,
                            phoneNumber = phoneNumber,
                            phonePrefix = phonePrefix,
                            onPhoneNumberChange = {
                                phoneNumber = it
                            },
                            loading = loading,
                            onLoading = {
                                loading = true
                            }
                        )
                    }
                    0 -> {
                        PhoneCodeStep(
                            vm = authViewModel,
                            phone = phone,
                            error = phoneCodeError,
                            loading = loading,
                            onLoading = {
                                loading = true
                            }
                        )
                    }
                    1 -> {
                        PasswordStep(
                            vm = authViewModel,
                            phone = phone,
                            loading = loading,
                            onLoading = {
                                loading = true
                            },
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun PhoneNumberStep(
    vm: AuthViewModel,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    phonePrefix: String,
    loading: Boolean,
    onLoading: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center
    ) {

        AppText(text = "شماره موبایل خود را وارد کنید:")
        Spacer.LargeSpacer()
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = phoneNumber,
                onValueChange = onPhoneNumberChange,
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone,),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.White
                )
            )
        }
        Spacer.VeryLargeSpacer()
        LoadingButton(
            modifier = Modifier.fillMaxWidth(),
            content = {
                AppText("ادامه")
            },
            onTap = {
                onLoading()
                vm.sendPhoneNumber(
                    mergePhoneNumber(
                        code = phonePrefix,
                        phoneNumber = phoneNumber
                    )
                )
            },
            loading = loading
        )
    }
}

@Composable
fun PhoneCodeStep(
    vm: AuthViewModel,
    phone: String,
    error: Boolean,
    loading: Boolean,
    onLoading: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppText("کد ارسال شده را وارد کنید:")
        Spacer.LargeSpacer()
        Otp(
            count = 5,
            onFinish = {
                onLoading()
                vm.sendPhoneCode(
                    phoneNumber = phone,
                    code = it
                )
            },
            error = error
        )
    }
}

@Composable
fun PasswordStep(
    vm: AuthViewModel,
    phone: String,
    loading: Boolean,
    onLoading: () -> Unit,
) {
    var password by State("")
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppText("حساب شما دارای ورود دو مرحله ای است، رمزعبور خود را وارد کنید:")
        Spacer.LargeSpacer()
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = password, onValueChange = {
                password = it
            },
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer.VeryLargeSpacer()
        LoadingButton(
            modifier = Modifier.fillMaxWidth(),
            content = {
                AppText("ادامه")
            },
            onTap = {
                onLoading()
                vm.sendPassword(password, phone)
            },
            loading = loading
        )

    }
}