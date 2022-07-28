package com.gplay.core.util

import com.gplay.core.domain.auth.Token
import kotlinx.datetime.Instant

object TestSamples {
    // Auth
    const val tokenString = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImpvaG4uZG9lQG1haWwuY29tIiwiZnVsbG5hbWUiOiJKb2huIERvZSJ9.z5D0sySgc4zqI0zL1dakHVMYaYrPPtoAFQvZGKL7kTg"
    val token = Token(
        accessToken = tokenString,
        refreshToken = tokenString,
        tokenType = "Bearer",
        expiresIn = Instant.parse("2022-12-31T12:00:00.000124Z"),
    )
    const val username = "john.doe@mail.com"
    const val password = "qwerty"
}
