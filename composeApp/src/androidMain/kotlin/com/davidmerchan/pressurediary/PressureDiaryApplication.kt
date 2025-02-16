package com.davidmerchan.pressurediary

import android.app.Application
import org.koin.android.ext.koin.androidContext
import com.davidmerchan.pressurediary.di.initializeKoin

class PressureDiaryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin(
            config = {
                androidContext(this@PressureDiaryApplication)
            }
        )
    }
}
