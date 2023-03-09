package ir.ehsan.telegram.free.screens.auth.models

import com.beust.klaxon.Json

data class AuthError(
    @Json("message")
    val message:String,
    @Json("code")
    val code:Int
)
