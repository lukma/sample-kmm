package com.gplay.core.domain.common.exception

sealed class APIError(override val message: String) : Error(message) {
    data class DatabaseFailure(override val cause: Throwable) : APIError(cause.message.toString())
    data class InvalidJson(override val message: String) : APIError(message)
    data class ServerProblem(override val message: String, val statusCode: Int) : APIError(message)
    data class InvalidToken(override val message: String) : APIError(message)
    data class UsernameNotFound(override val message: String) : APIError(message)
    data class PasswordMissmatch(override val message: String) : APIError(message)
    data class AccountInactive(override val message: String) : APIError(message)
}
