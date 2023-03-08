package ir.ehsan.telegram.free.utils

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
        }
    }
}