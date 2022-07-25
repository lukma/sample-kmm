package com.gplay.core.data.auth

import com.gplay.core.domain.auth.AuthRepository

internal class AuthRepositoryImpl(
    private val localAuthDataSource: AuthDataSource,
    private val networkAuthDataSource: AuthDataSource,
) : AuthRepository {
}
