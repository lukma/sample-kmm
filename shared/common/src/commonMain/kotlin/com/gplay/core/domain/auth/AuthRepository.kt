package com.gplay.core.domain.auth

import kotlinx.coroutines.flow.Flow

internal interface AuthRepository {
    suspend fun signIn(username: String, password: String): Flow<Token>
    suspend fun storeToken(username: String, token: Token)
}
