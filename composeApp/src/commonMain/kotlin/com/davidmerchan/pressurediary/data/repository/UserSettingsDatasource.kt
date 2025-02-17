package com.davidmerchan.pressurediary.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.davidmerchan.pressurediary.domain.model.UserSettingsModel
import com.davidmerchan.pressurediary.domain.repository.UserSettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserSettingsDatasource(
    private val dataStore: DataStore<Preferences>
): UserSettingsRepository {

    private val uidKey = stringPreferencesKey("UID_KEY")
    private val ageKey = intPreferencesKey("AGE_KEY")
    private val weightKey = doublePreferencesKey("WEIGHT_KEY")
    private val heightKey = doublePreferencesKey("HEIGHT_KEY")
    private val smokeKey = booleanPreferencesKey("SMOKE_KEY")
    private val genderKey = intPreferencesKey("GENDER_KEY")

    override suspend fun saveUserSettings(userSettings: UserSettingsModel): Result<Boolean> {
        return try {
            dataStore.edit { preferences ->
                preferences[uidKey] = userSettings.uid
                preferences[ageKey] = userSettings.age
                preferences[weightKey] = userSettings.weight
                preferences[heightKey] = userSettings.height
                preferences[smokeKey] = userSettings.smoke
                preferences[genderKey] = userSettings.gender
            }
            Result.success(true)
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserSettings(): Result<UserSettingsModel> {
        return try {
            val uid = dataStore.data.map { it[uidKey] ?: "" }.first()
            val age = dataStore.data.map { it[ageKey] ?: 0 }.first()
            val weight = dataStore.data.map { it[weightKey] ?: 0.0 }.first()
            val height = dataStore.data.map { it[heightKey] ?: 0.0 }.first()
            val smoke = dataStore.data.map { it[smokeKey] ?: false }.first()
            val gender = dataStore.data.map { it[genderKey] ?: 0 }.first()

            Result.success(UserSettingsModel(uid, age, weight, height, smoke, gender))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}