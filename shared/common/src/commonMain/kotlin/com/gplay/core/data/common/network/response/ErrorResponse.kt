package com.gplay.core.data.common.network.response

import com.gplay.core.domain.common.exception.APIError
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ErrorResponse(
    @SerialName("error_code")
    val code: ErrorCode,
    @SerialName("error_description")
    val description: String,
)

@Serializable
internal enum class ErrorCode {
    @SerialName("GE-101")
    TokenInvalid,

    @SerialName("GE-201")
    UsernameNotFound,

    @SerialName("GE-202")
    PasswordMissmatch,

    @SerialName("GE-301")
    AccountInactive,
}

internal fun ErrorResponse.toApiError(): APIError {
    return when (code) {
        ErrorCode.TokenInvalid -> APIError.InvalidToken(message = description)
        ErrorCode.UsernameNotFound -> APIError.UsernameNotFound(message = description)
        ErrorCode.PasswordMissmatch -> APIError.PasswordMissmatch(message = description)
        ErrorCode.AccountInactive -> APIError.AccountInactive(message = description)
    }
}
