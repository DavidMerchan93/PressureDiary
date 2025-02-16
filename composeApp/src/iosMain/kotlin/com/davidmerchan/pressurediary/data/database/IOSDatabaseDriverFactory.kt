package com.davidmerchan.pressurediary.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.davidmerchan.pressurediary.database.PressureDiaryDatabase

class IOSDatabaseDriverFactory: DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            PressureDiaryDatabase.Schema,
            "pressure_diary.db"
        )
    }
}