package com.gplay.core.data.common.network.http

import com.gplay.core.data.common.network.response.ErrorResponse
import com.gplay.core.data.common.network.response.toApiError
import com.gplay.core.domain.common.exception.APIError
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.resources.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.SerializationException
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

    HttpResponseValidator {
        handleResponseExceptionWithRequest { cause, _ ->
            throw when (cause) {
                is SerializationException -> cause.toAPIError()
                is ClientRequestException -> cause.toAPIError()
                is ServerResponseException -> cause.toAPIError()
                else -> cause
            }
        }
    }
}

private fun SerializationException.toAPIError(): APIError =
    APIError.InvalidJson(message.orEmpty())

private suspend fun ClientRequestException.toAPIError(): APIError =
    runCatching {
        response.body<ErrorResponse>().toApiError()
    }.getOrElse {
        (it as? SerializationException)?.toAPIError() ?: throw it
    }

private fun ServerResponseException.toAPIError() = APIError.ServerProblem(
    message = message,
    statusCode = response.status.value,
)
