package com.davidmerchan.pressurediary.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.davidmerchan.pressurediary.data.database.DatabaseDriverFactory
import com.davidmerchan.pressurediary.data.database.IOSDatabaseDriverFactory
import com.davidmerchan.pressurediary.data.local.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val targetModule: Module
    get() = module {
        // Data
        single<DatabaseDriverFactory> {
            IOSDatabaseDriverFactory()
        }
        single<DataStore<Preferences>> { createDataStore() }
    }