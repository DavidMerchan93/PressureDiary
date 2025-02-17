package com.davidmerchan.pressurediary.presentation.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmerchan.pressurediary.domain.useCase.GetUserSettingsUseCase
import com.davidmerchan.pressurediary.domain.useCase.SaveUserSettingsUseCase
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getUserSettingsUseCase: GetUserSettingsUseCase,
    private val saveUserSettingsUseCase: SaveUserSettingsUseCase
) : ViewModel() {

    var settingsState = mutableStateOf(SettingsScreenState())
        private set

    fun handleEvent(event: SettingsScreenEvents) {
        when (event) {
            is SettingsScreenEvents.LoadData -> getUserSettings()
            is SettingsScreenEvents.SaveUserSettings -> {
                saveUserSettings(
                    event.uid,
                    event.age,
                    event.weight,
                    event.height,
                    event.smoke,
                    event.gender
                )
            }
        }
    }

    private fun getUserSettings() {
        viewModelScope.launch {
            val result = getUserSettingsUseCase()
            when {
                result.isSuccess -> {
                    settingsState.value = SettingsScreenState(
                        userData = result.getOrNull()
                    )
                }

                result.isFailure -> {
                    println(result.exceptionOrNull()?.message)
                }
            }
        }
    }

    private fun saveUserSettings(
        uid: String,
        age: Int,
        weight: Double,
        height: Double,
        smoke: Boolean,
        gender: Gender
    ) {
        viewModelScope.launch {
            val result = saveUserSettingsUseCase(uid, age, weight, height, smoke, gender.id)
            when {
                result.isSuccess -> {
                    settingsState.value = SettingsScreenState(isSaved = true)
                }

                result.isFailure -> {
                    settingsState.value = SettingsScreenState(isSaved = false)
                    println(result.exceptionOrNull()?.message)
                }
            }
        }
    }
}
