package com.davidmerchan.pressurediary.di

import com.davidmerchan.pressurediary.data.database.LocalDataSource
import com.davidmerchan.pressurediary.data.database.LocalDatabase
import com.davidmerchan.pressurediary.data.repository.PressureLogDatasource
import com.davidmerchan.pressurediary.data.repository.UserSettingsDatasource
import com.davidmerchan.pressurediary.domain.repository.PressureLogRepository
import com.davidmerchan.pressurediary.domain.repository.UserSettingsRepository
import com.davidmerchan.pressurediary.domain.useCase.GetAllRecordsUseCase
import com.davidmerchan.pressurediary.domain.useCase.GetHomeRecordsUseCase
import com.davidmerchan.pressurediary.domain.useCase.GetIMCUseCase
import com.davidmerchan.pressurediary.domain.useCase.GetUserSettingsUseCase
import com.davidmerchan.pressurediary.domain.useCase.HasCardiovascularRiskUserCase
import com.davidmerchan.pressurediary.domain.useCase.InsertNewRecordUseCase
import com.davidmerchan.pressurediary.domain.useCase.SaveUserSettingsUseCase
import com.davidmerchan.pressurediary.presentation.home.HomeViewModel
import com.davidmerchan.pressurediary.presentation.newRecord.NewRecordViewModel
import com.davidmerchan.pressurediary.presentation.settings.SettingsViewModel
import com.davidmerchan.pressurediary.presentation.theme.history.HistoryViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

expect val targetModule: Module

val sharedModule = module {
    //Dispatcher
    factory { DispatcherProvider() }

    // Data
    single<LocalDataSource> { LocalDatabase(get()) }
    single<PressureLogRepository> { PressureLogDatasource(get(), get()) }
    single<UserSettingsRepository> { UserSettingsDatasource(get(), get()) }

    // Domain
    single { GetHomeRecordsUseCase(get()) }
    single { GetAllRecordsUseCase(get()) }
    single { InsertNewRecordUseCase(get()) }
    single { GetUserSettingsUseCase(get()) }
    single { SaveUserSettingsUseCase(get()) }
    single { GetIMCUseCase(get()) }
    single { HasCardiovascularRiskUserCase(get(), get(), get()) }

    // Presentation
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { NewRecordViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { SettingsViewModel(get(), get()) }
}

fun initializeKoin(config: (KoinApplication.() -> Unit)? = null) {
    startKoin {
        config?.invoke(this)
        modules(targetModule, sharedModule)
    }
}
