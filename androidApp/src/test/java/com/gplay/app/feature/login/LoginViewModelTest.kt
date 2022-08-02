package com.gplay.app.feature.login

import com.gplay.app.util.CoroutinesTestRule
import com.gplay.app.util.TestSamples
import com.gplay.core.domain.auth.usecase.SignInUseCase
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LoginViewModelTest {
    private val signInUseCase: SignInUseCase = mockk()
    private lateinit var viewModel: LoginViewModel

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setup() {
        viewModel = LoginViewModel(signInUseCase)
    }

    @Test
    fun `perform on username changed`() {
        // when
        viewModel.onUsernameChanged(username = TestSamples.username)
        val actual = viewModel.uiState.value

        // then
        val expected = TestSamples.username
        assertEquals(expected, actual.username)
    }

    @Test
    fun `perform on password changed`() {
        // when
        viewModel.onPasswordChanged(password = TestSamples.password)
        val actual = viewModel.uiState.value

        // then
        val expected = TestSamples.password
        assertEquals(expected, actual.password)
    }

    @Test
    fun `perform sign in got success`() {
        // given
        coEvery { signInUseCase(any()) } returns Result.success(Unit)

        // when
        viewModel.onUsernameChanged(username = TestSamples.username)
        viewModel.onPasswordChanged(password = TestSamples.password)
        viewModel.signIn()
        val actual = viewModel.uiState.value

        // then
        assertTrue(actual.isSignedIn)
        val paramSlot = CapturingSlot<SignInUseCase.Param>()
        coVerify { signInUseCase(capture(paramSlot)) }
        assertEquals(TestSamples.username, paramSlot.captured.username)
        assertEquals(TestSamples.password, paramSlot.captured.password)
    }

    @Test
    fun `perform sign in got failure`() {
        // given
        coEvery { signInUseCase(any()) } returns Result.failure(TestSamples.error)

        // when
        viewModel.signIn()
        val actual = viewModel.uiState.value

        // then
        assertFalse(actual.isSignedIn)
        assertEquals(TestSamples.error, actual.error)
    }
}
