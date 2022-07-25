package com.gplay.core.util

import app.cash.sqldelight.db.SqlDriver
import com.gplay.core.data.db.AppDatabase
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

interface DatabaseTest : KoinTest {

    @BeforeTest
    fun setup() {
        val databaseModule = module {
            single { createTestSqlDriver() }
            single {
                AppDatabase.Schema.create(get())
                AppDatabase(get())
            }
        }
        startKoin {
            modules(databaseModule)
        }
    }

    @AfterTest
    fun tearDown() {
        get<SqlDriver>().close()
        stopKoin()
    }
}
