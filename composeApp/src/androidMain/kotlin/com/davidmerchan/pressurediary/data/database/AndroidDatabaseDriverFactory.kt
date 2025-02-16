package com.davidmerchan.pressurediary.data.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.davidmerchan.pressurediary.database.PressureDiaryDatabase

class AndroidDatabaseDriverFactory(
    private val context: Context
) : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        // Implement Android specific database driver creation logic
        return AndroidSqliteDriver(
            PressureDiaryDatabase.Schema,
            context = context,
            name = "pressure_diary.db"
        )
    }
}