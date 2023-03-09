package ir.ehsan.telegram.free.composables.otp

import androidx.compose.ui.focus.FocusRequester

data class OtpValue(
    val value: String = "",
    var focused: Boolean = false,
    val focusRequester: FocusRequester? = null,
)