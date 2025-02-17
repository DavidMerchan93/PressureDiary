package com.davidmerchan.pressurediary.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createDataStore(context: Context): DataStore<Preferences> {
    return createDataStore(
        producePath = {
            context.filesDir.resolve(DATASTORE_FILE_NAME).absolutePath
        }
    )
}
