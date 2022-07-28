package com.gplay.core.data.auth

import com.gplay.core.domain.auth.Token
import kotlinx.coroutines.flow.Flow

internal interface AuthDataSource {
    suspend fun signIn(username: String, password: String): Flow<Token>
    suspend fun storeToken(username: String, token: Token)
}
