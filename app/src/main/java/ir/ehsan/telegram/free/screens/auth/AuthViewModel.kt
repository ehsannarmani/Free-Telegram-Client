package ir.ehsan.telegram.free.screens.auth

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import io.socket.client.IO
import io.socket.client.Socket
import ir.ehsan.telegram.free.screens.auth.models.AuthData
import ir.ehsan.telegram.free.screens.auth.models.AuthError
import ir.ehsan.telegram.free.screens.auth.models.Session
import ir.ehsan.telegram.free.screens.auth.models.Status
import ir.ehsan.telegram.free.utils.Constants
import ir.ehsan.telegram.free.utils.emitWithSerialize
import ir.ehsan.telegram.free.utils.onWithSerialize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class AuthViewModel: ViewModel(),KoinComponent {
    private val sharedPreferences:SharedPreferences = get()

    lateinit var socket:Socket

    private val _socketConnected:MutableStateFlow<Boolean> = MutableStateFlow(false)
    val socketConnected = _socketConnected.asStateFlow()

    private val _step:MutableStateFlow<Int> = MutableStateFlow(-1)
    val step = _step.asStateFlow()

    val userLoggedIn:Boolean get() =
        sharedPreferences.getBoolean(Constants.USER_LOGGED_IN,false)


    private val EVENT_STATUS = "status"
    private val EVENT_ERROR = "authError"
    private val EVENT_LOGGED_IN = "loggedIn"

    private val EVENT_AUTH_PHONE_NUMBER = "authPhoneNumber"
    private val EVENT_AUTH_PHONE_CODE = "authPhoneCode"
    private val EVENT_AUTH_PASSWORD = "authPassword"
    fun init(
        onConnect:()->Unit = {},
        onDisconnect:()->Unit = {},
        onLoggedIn:(String)->Unit,
        onStepChanged:(Status)->Unit,
        onErrorThrown:(AuthError)->Unit
    ){
        socket = IO.socket(Constants.SOCKET_URL)
        socket.connect()
        socket.on(Socket.EVENT_CONNECT){
            _socketConnected.value = true
            onConnect()
        }

        socket.on(Socket.EVENT_DISCONNECT){
            _socketConnected.value = false
            onDisconnect()
        }

        socket.onWithSerialize<Session>(EVENT_LOGGED_IN){
            onLoggedIn(it.session)
        }

        socket.onWithSerialize<Status>(EVENT_STATUS){
            _step.value = it.code
            onStepChanged(it)
        }

        socket.onWithSerialize(EVENT_ERROR,onErrorThrown)
    }
    fun sendPhoneNumber(phoneNumber:String){
        socket.emit(EVENT_AUTH_PHONE_NUMBER,phoneNumber)
    }
    fun sendPhoneCode(code:String,phoneNumber: String){
        socket.emitWithSerialize(EVENT_AUTH_PHONE_CODE,AuthData(data = code,phoneNumber))
    }
    fun sendPassword(password:String,phoneNumber: String){
        socket.emitWithSerialize(EVENT_AUTH_PASSWORD,AuthData(data = password,phoneNumber))
    }


}