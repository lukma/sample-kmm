package com.gplay.core.domain.common.exception

sealed class APIError(override val message: String) : Error(message) {
    data class InvalidJson(override val message: String) : APIError(message)
    data class ServerProblem(override val message: String, val statusCode: Int) : APIError(message)
}
