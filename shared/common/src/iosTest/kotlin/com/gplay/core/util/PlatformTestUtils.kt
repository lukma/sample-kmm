package com.gplay.core.util

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.gplay.core.config.Constants
import com.gplay.core.data.db.AppDatabase

actual fun createTestSqlDriver(): SqlDriver =
    NativeSqliteDriver(AppDatabase.Schema, Constants.databaseName)
