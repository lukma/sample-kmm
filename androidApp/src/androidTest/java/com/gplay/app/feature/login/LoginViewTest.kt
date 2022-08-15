package com.gplay.app.feature.login

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.gplay.app.ui.GPlayScaffold
import com.gplay.app.ui.theme.GPlayTheme
import com.gplay.app.util.TestSamples
import com.gplay.core.domain.auth.usecase.SignInUseCase
import com.gplay.core.domain.validation.usecase.FormValidationUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewTest {
    private val formValidationUseCase = FormValidationUseCase()
    private val signInUseCase: SignInUseCase = mockk()
    private val onSigned: () -> Unit = mockk(relaxed = true)
    private lateinit var viewModel: LoginViewModel

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        viewModel = LoginViewModel(formValidationUseCase, signInUseCase)
        composeTestRule.setContent {
            GPlayTheme {
                GPlayScaffold {
                    val uiState by viewModel.uiState.collectAsState()
                    LoginView(
                        uiState = uiState,
                        onSendEvent = viewModel::sendEvent,
                        onSigned = onSigned,
                    )
                }
            }
        }
    }

    @Test
    fun perform_type_username_with_valid_value() {
        // when
        composeTestRule.onNode(hasTestTag("UsernameTextField"))
            .performTextInput(TestSamples.username)

        // then
        composeTestRule.onNode(hasTestTag("UsernameTextField"))
            .assertTextContains(TestSamples.username)
        composeTestRule.onNode(hasTestTag("UsernameTextFieldError"))
            .assertIsNotDisplayed()
    }

    @Test
    fun perform_type_username_with_empty_value() {
        // when
        composeTestRule.onNode(hasTestTag("UsernameTextField"))
            .performTextInput(TestSamples.username)
        composeTestRule.onNode(hasTestTag("UsernameTextField"))
            .performTextClearance()

        // then
        composeTestRule.onNode(hasTestTag("UsernameTextFieldError"))
            .assertIsDisplayed()
    }

    @Test
    fun perform_type_password_with_valid_value() {
        // when
        composeTestRule.onNode(hasTestTag("PasswordTextField"))
            .performTextInput(TestSamples.password)

        // then
        composeTestRule.onNode(hasTestTag("PasswordTextField"))
            .assertTextContains("••••••")
        composeTestRule.onNode(hasTestTag("PasswordTextFieldError"))
            .assertIsNotDisplayed()
    }

    @Test
    fun perform_type_password_with_empty_value() {
        // when
        composeTestRule.onNode(hasTestTag("PasswordTextField"))
            .performTextInput(TestSamples.password)
        composeTestRule.onNode(hasTestTag("PasswordTextField"))
            .performTextClearance()

        // then
        composeTestRule.onNode(hasTestTag("PasswordTextFieldError"))
            .assertIsDisplayed()
    }

    @Test
    fun perform_sign_in_got_success() {
        // given
        coEvery { signInUseCase(any()) } returns Result.success(Unit)

        // when
        composeTestRule.onNode(hasTestTag("SignInButton"))
            .assertIsNotEnabled()
        composeTestRule.onNode(hasTestTag("UsernameTextField"))
            .performTextInput(TestSamples.username)
        composeTestRule.onNode(hasTestTag("PasswordTextField"))
            .performTextInput(TestSamples.password)
        composeTestRule.onNode(hasTestTag("SignInButton"))
            .assertIsEnabled()
            .performClick()

        // then
        composeTestRule.onNode(hasTestTag("Loading"))
            .assertIsDisplayed()
        composeTestRule.mainClock.advanceTimeByFrame()
        verify(exactly = 1) { onSigned() }
    }

    @Test
    fun perform_sign_in_got_failure() {
        // given
        coEvery { signInUseCase(any()) } returns Result.failure(TestSamples.error)

        // when
        composeTestRule.onNode(hasTestTag("UsernameTextField"))
            .performTextInput(TestSamples.username)
        composeTestRule.onNode(hasTestTag("PasswordTextField"))
            .performTextInput(TestSamples.password)
        composeTestRule.onNode(hasTestTag("SignInButton"))
            .performClick()

        // then
        composeTestRule.onNode(hasText(TestSamples.error.message.orEmpty()))
            .assertIsDisplayed()
    }
}
