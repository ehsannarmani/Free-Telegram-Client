package ir.ehsan.telegram.free.screens.auth.models

import com.beust.klaxon.Json

data class Status(
    @Json("name")
    val name: String,
    @Json("code")
    val code: Int
)
