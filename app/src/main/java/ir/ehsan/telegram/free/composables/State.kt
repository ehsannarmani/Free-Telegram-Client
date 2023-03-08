package ir.ehsan.telegram.free.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun <T>State(value:T) = remember {
    mutableStateOf(value)
}

@Composable
fun <T>ListState(vararg values:T) = remember {
    mutableStateListOf(*values)
}