package ir.ehsan.telegram.free.screens.auth.models

import com.beust.klaxon.Json

data class Session(
    @Json("session")
    val session: String
)
