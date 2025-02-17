package com.davidmerchan.pressurediary

import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.mutablePreferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import com.davidmerchan.pressurediary.data.database.LocalDataSource
import com.davidmerchan.pressurediary.data.repository.PressureLogDatasource
import com.davidmerchan.pressurediary.data.repository.UserSettingsDatasource
import com.davidmerchan.pressurediary.di.DispatcherProvider
import com.davidmerchan.pressurediary.di.initializeKoin
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseTest : KoinTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private val sharedModule = module {

        factory {
            DispatcherProvider(
                main = dispatcher,
                io = dispatcher,
                default = dispatcher
            )
        }

        single<DataStore<Preferences>> {
            FakeDataStore(
                initialPrefs = mutablePreferencesOf(
                    stringPreferencesKey("UID_KEY") to "123456",
                    intPreferencesKey("AGE_KEY") to 32,
                    doublePreferencesKey("WEIGHT_KEY") to 60.5,
                    doublePreferencesKey("HEIGHT_KEY") to 1.70,
                    booleanPreferencesKey("SMOKE_KEY") to false,
                    intPreferencesKey("GENDER_KEY") to 0,
                )
            )
        }
        single<LocalDataSource> { FakeLocalDataSource() }
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
        single { HomeViewModel(get(), get(), get()) }
        single { NewRecordViewModel(get()) }
        single { HistoryViewModel(get()) }
        single { SettingsViewModel(get(), get()) }
    }

    @BeforeTest
    fun setup() {
        startKoin {
            modules(sharedModule)
        }
        Dispatchers.setMain(dispatcher)
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }


    fun <I> every(input: I, success: Boolean): I {
        if (success) {
            return input
        } else {
            throw Exception("Error in process")
        }
    }
}
