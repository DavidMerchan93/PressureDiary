package com.davidmerchan.pressurediary.di

import com.davidmerchan.pressurediary.data.database.LocalDataSource
import com.davidmerchan.pressurediary.data.database.LocalDatabase
import com.davidmerchan.pressurediary.data.model.HealthCareApi
import com.davidmerchan.pressurediary.data.network.NetworkConfig
import com.davidmerchan.pressurediary.data.repository.HealthCareTipsDatasource
import com.davidmerchan.pressurediary.data.repository.PressureLogDatasource
import com.davidmerchan.pressurediary.data.repository.UserSettingsDatasource
import com.davidmerchan.pressurediary.domain.repository.HealthCareTipsRepository
import com.davidmerchan.pressurediary.domain.repository.PressureLogRepository
import com.davidmerchan.pressurediary.domain.repository.UserSettingsRepository
import com.davidmerchan.pressurediary.domain.useCase.GetAllRecordsUseCase
import com.davidmerchan.pressurediary.domain.useCase.GetHealthCareTipUseCase
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
import io.ktor.client.HttpClient
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

expect val targetModule: Module

private val sharedModule = module {
    //Dispatcher
    factory { DispatcherProvider() }

    // Api
    single<HttpClient> { NetworkConfig.createHttpClient() }
    single { HealthCareApi(get()) }
}

private val dataModule = module {
    single<LocalDataSource> { LocalDatabase(get()) }
    single<PressureLogRepository> { PressureLogDatasource(get(), get()) }
    single<UserSettingsRepository> { UserSettingsDatasource(get(), get()) }
    single<HealthCareTipsRepository> { HealthCareTipsDatasource(get(), get()) }
}

private val domainModule = module {
    single { GetHomeRecordsUseCase(get()) }
    single { GetAllRecordsUseCase(get()) }
    single { InsertNewRecordUseCase(get()) }
    single { GetUserSettingsUseCase(get()) }
    single { SaveUserSettingsUseCase(get()) }
    single { GetIMCUseCase(get()) }
    single { HasCardiovascularRiskUserCase(get(), get(), get()) }
    single { GetHealthCareTipUseCase(get()) }
}

private val presentationModule = module {
    viewModel { HomeViewModel(get(), get(), get(), get()) }
    viewModel { NewRecordViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { SettingsViewModel(get(), get()) }
}

fun initializeKoin(config: (KoinApplication.() -> Unit)? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            targetModule,
            sharedModule,
            dataModule,
            domainModule,
            presentationModule
        )
    }
}
