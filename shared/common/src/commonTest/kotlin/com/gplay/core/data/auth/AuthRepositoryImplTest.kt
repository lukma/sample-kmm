package com.gplay.core.data.auth

import com.gplay.core.domain.auth.AuthRepository
import com.gplay.core.util.TestSamples
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

class AuthRepositoryImplTest {
    private val localAuthDataSource: AuthDataSource = mockk()
    private val networkAuthDataSource: AuthDataSource = mockk()
    private val authRepository: AuthRepository = AuthRepositoryImpl(
        localAuthDataSource,
        networkAuthDataSource,
    )

    @Test
    fun `perform sign in got value`() = runTest {
        // given
        coEvery {
            networkAuthDataSource.signIn(any(), any())
        } returns flow { emit(TestSamples.token) }

        // when
        val actual = authRepository.signIn(
            username = TestSamples.username,
            password = TestSamples.password,
        ).single()

        // then
        val expected = TestSamples.token
        assertEquals(expected, actual)
        coVerify {
            networkAuthDataSource.signIn(
                username = TestSamples.username,
                password = TestSamples.password,
            )
        }
    }

    @Test
    fun `perform sign in got failure`() = runTest {
        // given
        coEvery { networkAuthDataSource.signIn(any(), any()) } returns flow { throw Error() }

        // when
        val actual = runCatching {
            authRepository.signIn(
                username = TestSamples.username,
                password = TestSamples.password,
            ).single()
        }

        // then
        assertFails { actual.getOrThrow() }
    }

    @Test
    fun `perform store token got success`() = runTest {
        // given
        coJustRun { localAuthDataSource.storeToken(any(), any()) }

        // when
        authRepository.storeToken(username = TestSamples.username, token = TestSamples.token)

        // then
        coVerify {
            localAuthDataSource.storeToken(
                username = TestSamples.username,
                token = TestSamples.token,
            )
        }
    }

    @Test
    fun `perform store token got failure`() = runTest {
        // given
        coEvery { localAuthDataSource.storeToken(any(), any()) } throws Error()

        // when
        val actual = runCatching {
            authRepository.storeToken(username = TestSamples.username, token = TestSamples.token)
        }

        // then
        assertFails { actual.getOrThrow() }
    }

    @Test
    fun `check is signed in got value`() = runTest {
        // given
        coEvery { localAuthDataSource.isSignedIn() } returns flow { emit(true) }

        // when
        val actual = authRepository.isSignedIn().single()

        // then
        assertTrue(actual)
        coVerify { localAuthDataSource.isSignedIn() }
    }

    @Test
    fun `check is signed in got failure`() = runTest {
        // given
        coEvery { localAuthDataSource.isSignedIn() } returns flow { throw Error() }

        // when
        val actual = runCatching {
            authRepository.isSignedIn().single()
        }

        // then
        assertFails { actual.getOrThrow() }
    }
}
