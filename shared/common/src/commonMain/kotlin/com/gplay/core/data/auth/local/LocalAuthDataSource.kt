package com.gplay.core.data.auth.local

import com.gplay.core.data.auth.AuthDataSource
import com.gplay.core.data.db.AppDatabase

internal class LocalAuthDataSource(private val database: AppDatabase) : AuthDataSource {
}
