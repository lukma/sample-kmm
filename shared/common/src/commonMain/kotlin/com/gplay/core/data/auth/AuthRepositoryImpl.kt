package com.gplay.core.data.auth

import com.gplay.core.domain.auth.AuthRepository
import com.gplay.core.domain.auth.Token
import kotlinx.coroutines.flow.Flow

internal class AuthRepositoryImpl(
    private val localAuthDataSource: AuthDataSource,
    private val networkAuthDataSource: AuthDataSource,
) : AuthRepository {

    override suspend fun signIn(username: String, password: String): Flow<Token> {
        return networkAuthDataSource.signIn(username, password)
    }

    override suspend fun storeToken(username: String, token: Token) {
        localAuthDataSource.storeToken(username, token)
    }
}
