package ir.ehsan.telegram.free.screens.auth

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import ir.ehsan.telegram.free.utils.Constants
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject

class AuthViewModel: ViewModel(),KoinComponent {
    private val sharedPreferences:SharedPreferences = get()

    val userLoggedIn:Boolean get() =
        sharedPreferences.getBoolean(Constants.USER_LOGGED_IN,false)
}