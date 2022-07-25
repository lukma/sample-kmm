package com.gplay.core.data.auth.network

import com.gplay.core.data.auth.AuthDataSource
import io.ktor.client.*

internal class NetworkAuthDataSource(private val httpClient: HttpClient) : AuthDataSource {
}
