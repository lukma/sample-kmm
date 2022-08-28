package com.gplay.core.di

import com.gplay.core.data.db.AppDatabase
import org.koin.dsl.module

internal val databaseModule = module {
    single { AppDatabase(get()) }
}
