package com.gplay.app.initializer

import android.content.Context
import androidx.startup.Initializer
import com.gplay.app.BuildConfig
import com.gplay.core.config.AppConfig
import com.gplay.core.initKoin
import org.koin.android.ext.koin.androidContext

class KoinInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val appConfig = AppConfig(
            apiHost = BuildConfig.API_HOST,
        )
        initKoin(appConfig) {
            androidContext(context)
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}
