package com.davidmerchan.pressurediary.di

import com.davidmerchan.pressurediary.data.database.LocalDataSource
import com.davidmerchan.pressurediary.data.database.LocalDatabase
import com.davidmerchan.pressurediary.data.repository.PressureLogDatasource
import com.davidmerchan.pressurediary.domain.repository.PressureLogRepository
import com.davidmerchan.pressurediary.domain.useCase.GetAllRecordsUseCase
import com.davidmerchan.pressurediary.domain.useCase.GetHomeRecordsUseCase
import com.davidmerchan.pressurediary.domain.useCase.InsertNewRecordUseCase
import com.davidmerchan.pressurediary.presentation.home.HomeViewModel
import com.davidmerchan.pressurediary.presentation.newRecord.NewRecordViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

expect val targetModule: Module

val sharedModule = module {
    // Data
    single<LocalDataSource> { LocalDatabase(get()) }
    single<PressureLogRepository> { PressureLogDatasource(get()) }

    // Domain
    single { GetHomeRecordsUseCase(get()) }
    single { GetAllRecordsUseCase(get()) }
    single { InsertNewRecordUseCase(get()) }

    // Presentation
    factory { HomeViewModel(get()) }
    factory { NewRecordViewModel(get()) }
}

fun initializeKoin(config: (KoinApplication.() -> Unit)? = null) {
    startKoin {
        config?.invoke(this)
        modules(targetModule, sharedModule)
    }
}
