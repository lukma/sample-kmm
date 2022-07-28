package com.gplay.core.data.auth.network

import com.gplay.core.data.auth.AuthDataSource
import com.gplay.core.data.auth.network.response.TokenResponse
import com.gplay.core.data.auth.network.response.toToken
import com.gplay.core.domain.auth.Token
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class NetworkAuthDataSource(private val httpClient: HttpClient) : AuthDataSource {
    override suspend fun signIn(username: String, password: String): Flow<Token> {
        return flow {
            val resource = AuthResource.SignIn(username = username, password = password)
            val response = httpClient.post(resource)
                .body<TokenResponse>()
                .toToken()
            emit(response)
        }
    }

    override suspend fun storeToken(username: String, token: Token) {
        throw NotImplementedError()
    }
}
