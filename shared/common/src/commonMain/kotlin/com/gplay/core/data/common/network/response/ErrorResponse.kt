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
}

internal fun ErrorResponse.toApiError(): APIError {
    return when (code) {
        else -> throw NotImplementedError()
    }
}
