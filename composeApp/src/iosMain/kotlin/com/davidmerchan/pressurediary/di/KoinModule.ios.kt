package com.davidmerchan.pressurediary.di

import com.davidmerchan.pressurediary.data.database.DatabaseDriverFactory
import com.davidmerchan.pressurediary.data.database.IOSDatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val targetModule: Module
    get() = module {
        // Data
        single<DatabaseDriverFactory> {
            IOSDatabaseDriverFactory()
        }
    }