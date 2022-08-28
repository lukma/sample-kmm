package com.gplay.core.domain.auth.usecase

import com.gplay.core.domain.auth.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import kotlin.test.assertFails
import kotlin.test.assertTrue

class IsSignedInUseCaseTest {
    private val authRepository: AuthRepository = mockk()
    private val useCase = IsSignedInUseCase(authRepository)

    @Test
    fun `check is signed in got value`() = runTest {
        // given
        coEvery { authRepository.isSignedIn() } returns flow { emit(true) }

        // when
        val actual = useCase().single()

        // then
        assertTrue(actual)
        coVerify { authRepository.isSignedIn() }
    }

    @Test
    fun `check is signed in got failure`() = runTest {
        // given
        coEvery { authRepository.isSignedIn() } returns flow { throw Error() }

        // when
        val actual = runCatching {
            useCase().single()
        }

        // then
        assertFails { actual.getOrThrow() }
    }
}
