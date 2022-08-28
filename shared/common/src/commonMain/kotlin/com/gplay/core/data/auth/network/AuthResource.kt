package com.gplay.core.data.auth.network

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/v1/auth")
internal class AuthResource {

    @Serializable
    @Resource("signin")
    class SignIn(
        val parent: AuthResource = AuthResource(),
        val username: String,
        val password: String,
    )
}
