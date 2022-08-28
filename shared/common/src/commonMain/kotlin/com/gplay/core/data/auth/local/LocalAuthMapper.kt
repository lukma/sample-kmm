package com.gplay.core.data.auth.local

import com.gplay.core.data.db.Auth
import com.gplay.core.domain.auth.Token
import kotlinx.datetime.toInstant

internal fun Token.toLocalActiveAuth(username: String) = Auth(
    username = username,
    accessToken = accessToken,
    refreshToken = refreshToken,
    tokenType = tokenType,
    expiresIn = expiresIn.toString(),
    isActive = true,
)

internal fun mapToToken(
    accessToken: String,
    refreshToken: String,
    tokenType: String,
    expiresIn: String,
) = Token(
    accessToken = accessToken,
    refreshToken = refreshToken,
    tokenType = tokenType,
    expiresIn = expiresIn.toInstant(),
)
