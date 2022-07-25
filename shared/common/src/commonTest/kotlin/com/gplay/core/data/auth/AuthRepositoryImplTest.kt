package com.gplay.core.data.auth

import com.gplay.core.domain.auth.AuthRepository
import io.mockk.mockk

class AuthRepositoryImplTest {
    private val localAuthDataSource: AuthDataSource = mockk()
    private val networkAuthDataSource: AuthDataSource = mockk()
    private val authRepository: AuthRepository = AuthRepositoryImpl(
        localAuthDataSource,
        networkAuthDataSource,
    )
}
