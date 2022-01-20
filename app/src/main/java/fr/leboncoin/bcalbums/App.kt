package fr.leboncoin.bcalbums

import android.app.Application
import fr.leboncoin.bcalbums.di.appModule
import fr.leboncoin.bcalbums.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    viewModelModule
                )
            )
        }
    }
}