package ir.ehsan.telegram.free.screens.auth.models

import com.beust.klaxon.Json

data class AuthData(
    @Json("data")
    val data:String,
    @Json("phoneNumber")
    val phoneNumber:String,
)
