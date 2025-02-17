package com.davidmerchan.pressurediary.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.davidmerchan.pressurediary.data.database.AndroidDatabaseDriverFactory
import com.davidmerchan.pressurediary.data.database.DatabaseDriverFactory
import com.davidmerchan.pressurediary.data.local.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val targetModule: Module
    get() = module {
        // Data
        single<DatabaseDriverFactory> {
            AndroidDatabaseDriverFactory(androidContext())
        }
        single<DataStore<Preferences>> { createDataStore(androidContext()) }
    }
