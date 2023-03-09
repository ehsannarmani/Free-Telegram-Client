package ir.ehsan.telegram.free.utils

import android.util.Log
import com.beust.klaxon.Klaxon
import io.socket.client.Socket

inline fun <reified T>Socket.onWithSerialize(event:String, crossinline onReceive:(T)->Unit){
    on(event){
        val data = it[0]
        runCatching {
            Klaxon().parse<T>(data.toString())
        }.onSuccess {parsed->
            if(parsed != null){
                onReceive(parsed)
            }
        }.onFailure {
            Log.e("tag",it.message.toString())
        }
    }
}

fun <T : Any>Socket.emitWithSerialize(event: String, data:T){
    emit(event,Klaxon().toJsonString(data))
}