package com.douglastaquary.crossfitprapp

import android.app.Application
import com.douglastaquary.crossfitprapp.di.appModule
import com.douglastaquary.crossfitprapp.di.appModule
import com.douglastaquary.crossfitprapp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CrossfitPRApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        initKoin {
            // https://github.com/InsertKoinIO/koin/issues/1188
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@CrossfitPRApplication)
            modules(appModule)
        }
    }
}
