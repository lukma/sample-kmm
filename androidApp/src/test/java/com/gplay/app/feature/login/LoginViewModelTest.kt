package com.gplay.app.feature.login

import com.gplay.app.util.CoroutinesTestRule
import com.gplay.app.util.TestSamples
import com.gplay.core.domain.auth.usecase.SignInUseCase
import com.gplay.core.domain.validation.*
import com.gplay.core.domain.validation.usecase.FormValidationUseCase
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class LoginViewModelTest {
    private val formValidationUseCase = FormValidationUseCase()
    private val signInUseCase: SignInUseCase = mockk()
    private lateinit var viewModel: LoginViewModel

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setup() {
        viewModel = LoginViewModel(formValidationUseCase, signInUseCase)
    }

    @Test
    fun `perform on username changed with valid value`() {
        // when
        viewModel.sendEvent(LoginUiEvent.TypeUsername(TestSamples.username))
        val actual = viewModel.uiState.value

        // then
        val expected = LoginUiState(
            username = TestSamples.username,
            validations = createValidationValues(
                custom = LoginFormSpec.Username to ValidationState.Valid,
            ),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `perform on username changed with empty value`() {
        // when
        viewModel.sendEvent(LoginUiEvent.TypeUsername(""))
        val actual = viewModel.uiState.value

        // then
        val expected = LoginUiState(
            validations = createValidationValues(
                custom = LoginFormSpec.Username to ValidationState.Invalid(ValidationError.FieldBlank),
            ),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `perform on password changed with valid value`() {
        // when
        viewModel.sendEvent(LoginUiEvent.TypePassword(TestSamples.password))
        val actual = viewModel.uiState.value

        // then
        val expected = LoginUiState(
            password = TestSamples.password,
            validations = createValidationValues(
                custom = LoginFormSpec.Password to ValidationState.Valid,
            ),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `perform on password changed with empty value`() {
        // when
        viewModel.sendEvent(LoginUiEvent.TypePassword(""))
        val actual = viewModel.uiState.value

        // then
        val expected = LoginUiState(
            validations = createValidationValues(
                custom = LoginFormSpec.Password to ValidationState.Invalid(ValidationError.FieldBlank),
                default = null,
            ),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `perform sign in got success`() {
        // given
        coEvery { signInUseCase(any()) } returns Result.success(Unit)

        // when
        viewModel.sendEvent(LoginUiEvent.TypeUsername(TestSamples.username))
        viewModel.sendEvent(LoginUiEvent.TypePassword(TestSamples.password))
        viewModel.sendEvent(LoginUiEvent.SignIn)
        val actual = viewModel.uiState.value

        // then
        val expected = LoginUiState(
            username = TestSamples.username,
            password = TestSamples.password,
            isSignedIn = true,
            validations = createValidationValues(default = ValidationState.Valid),
        )
        assertEquals(expected, actual)

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
        viewModel.sendEvent(LoginUiEvent.TypeUsername(TestSamples.username))
        viewModel.sendEvent(LoginUiEvent.TypePassword(TestSamples.password))
        viewModel.sendEvent(LoginUiEvent.SignIn)
        val actual = viewModel.uiState.value

        // then
        val expected = LoginUiState(
            username = TestSamples.username,
            password = TestSamples.password,
            error = TestSamples.error,
            validations = createValidationValues(default = ValidationState.Valid),
        )
        assertEquals(expected, actual)
    }

    private fun createValidationValues(
        custom: Pair<FieldSpec, ValidationState?>? = null,
        default: ValidationState? = null,
    ): ValidationStates {
        return LoginFormSpec.values().associate {
            it.key to if (it.key == custom?.first?.key) custom.second else default
        }
    }
}
