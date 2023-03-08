package ir.ehsan.telegram.free

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class FreeTelegram: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@FreeTelegram)
            val composableModule = module {
                single {  }
            }
            modules(module {
                single {
                    getSharedPreferences("main", Context.MODE_PRIVATE)
                }
                single {
                    HttpClient(CIO){
                        expectSuccess = false
                        install(WebSockets)
                    }
                }
            })
        }
    }
}
