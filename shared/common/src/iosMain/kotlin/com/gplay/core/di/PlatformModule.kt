package com.gplay.core.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.gplay.core.config.Constants
import com.gplay.core.data.db.AppDatabase
import io.ktor.client.engine.darwin.*
import org.koin.dsl.module

internal actual val platformModule = module {
    single<SqlDriver> { NativeSqliteDriver(AppDatabase.Schema, Constants.databaseName) }
    single { Darwin.create() }
}
