package com.gplay.core.domain.auth.usecase

import com.gplay.core.domain.auth.AuthRepository
import com.gplay.core.domain.common.entity.Result
import com.gplay.core.util.TestSamples
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals

class SignInUseCaseTest {
    private val authRepository: AuthRepository = mockk()
    private val useCase = SignInUseCase(authRepository)
    private val useCaseParam = SignInUseCase.Param(
        username = TestSamples.username,
        password = TestSamples.password,
    )

    @Test
    fun `perform sign in got success`() = runTest {
        // given
        coEvery { authRepository.signIn(any(), any()) } returns flow { emit(TestSamples.token) }
        coJustRun { authRepository.storeToken(any(), any()) }

        // when
        val actual = useCase(useCaseParam)

        // then
        val expected = Result.Success(Unit)
        assertEquals(expected, actual)
        coVerify {
            authRepository.signIn(username = TestSamples.username, password = TestSamples.password)
            authRepository.storeToken(username = TestSamples.username, token = TestSamples.token)
        }
    }

    @Test
    fun `perform sign in got failure`() = runTest {
        // given
        val error = Error()
        coEvery { authRepository.signIn(any(), any()) } returns flow { throw error }

        // when
        val actual = useCase(useCaseParam)

        // then
        val expected = Result.Failure(error)
        assertEquals(expected, actual)
    }
}
