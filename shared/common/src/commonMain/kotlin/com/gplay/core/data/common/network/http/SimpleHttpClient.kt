package com.gplay.core.data.common.network.http

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.resources.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

@Suppress("FunctionName")
internal fun SimpleHttpClient(
    engine: HttpClientEngine,
    block: HttpClientConfig<*>.() -> Unit = {},
): HttpClient = HttpClient(engine) {
    expectSuccess = true
    block.invoke(this)

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(Resources)
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.HEADERS
    }
}
