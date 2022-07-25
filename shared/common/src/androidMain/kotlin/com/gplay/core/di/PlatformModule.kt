package com.gplay.core.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.gplay.core.config.Constants
import com.gplay.core.data.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal actual val platformModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(AppDatabase.Schema, androidContext(), Constants.databaseName)
    }
}
