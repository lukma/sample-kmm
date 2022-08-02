package com.gplay.app.feature.login

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.gplay.app.main.MainUiEvent
import com.gplay.app.ui.GPlayScaffold
import com.gplay.app.ui.theme.GPlayTheme
import com.gplay.app.util.TestSamples
import com.gplay.core.domain.auth.usecase.SignInUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewTest {
    private val signInUseCase: SignInUseCase = mockk()
    private val onSendMainUiEvent: (MainUiEvent) -> Unit = mockk(relaxed = true)
    private lateinit var viewModel: LoginViewModel

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        viewModel = LoginViewModel(signInUseCase)
        composeTestRule.setContent {
            GPlayTheme {
                GPlayScaffold {
                    LoginView(viewModel, onSendMainUiEvent)
                }
            }
        }
    }

    @Test
    fun perform_sign_in_got_success() {
        // given
        coEvery { signInUseCase(any()) } returns Result.success(Unit)

        // when
        composeTestRule.onNode(hasTestTag("usernameTextField"))
            .performTextInput(TestSamples.username)
        composeTestRule.onNode(hasTestTag("passwordTextField"))
            .performTextInput(TestSamples.password)
        composeTestRule.onNode(hasTestTag("signInButton"))
            .performClick()

        // then
        composeTestRule.mainClock.advanceTimeByFrame()
        verify(exactly = 1) { onSendMainUiEvent(MainUiEvent.CheckIsSignedIn) }
    }

    @Test
    fun perform_sign_in_got_failure() {
        // given
        coEvery { signInUseCase(any()) } returns Result.failure(TestSamples.error)

        // when
        composeTestRule.onNode(hasTestTag("usernameTextField"))
            .performTextInput(TestSamples.username)
        composeTestRule.onNode(hasTestTag("passwordTextField"))
            .performTextInput(TestSamples.password)
        composeTestRule.onNode(hasTestTag("signInButton"))
            .performClick()

        // then
        composeTestRule.onNode(hasText(TestSamples.error.message.orEmpty()))
            .assertIsDisplayed()
    }
}
