package com.gplay.core.data.auth.network

import com.gplay.core.data.auth.AuthDataSource
import com.gplay.core.domain.auth.Token
import io.ktor.client.*
import kotlinx.coroutines.flow.Flow

internal class NetworkAuthDataSource(private val httpClient: HttpClient) : AuthDataSource {
    override suspend fun signIn(username: String, password: String): Flow<Token> {
        throw NotImplementedError()
    }

    override suspend fun storeToken(username: String, token: Token) {
        throw NotImplementedError()
    }
}
