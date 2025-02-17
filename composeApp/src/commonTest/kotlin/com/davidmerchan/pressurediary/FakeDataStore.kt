package com.davidmerchan.pressurediary

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.mutablePreferencesOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeDataStore(
    initialPrefs: Preferences = mutablePreferencesOf()
) : DataStore<Preferences> {
    private val _dataFlow = MutableStateFlow(initialPrefs)
    override val data: Flow<Preferences> = _dataFlow.asStateFlow()

    override suspend fun updateData(transform: suspend (Preferences) -> Preferences): Preferences {
        val newPrefs = transform(_dataFlow.value)
        _dataFlow.value = newPrefs
        return newPrefs
    }
}