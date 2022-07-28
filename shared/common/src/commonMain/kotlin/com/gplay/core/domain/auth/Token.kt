package com.gplay.core.domain.auth

import kotlinx.datetime.Instant

data class Token(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresIn: Instant,
)
