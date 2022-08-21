package com.gplay.app.feature.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.gplay.android.uikit.ui.PasswordTextField
import com.gplay.android.uikit.ui.UsernameTextField
import com.gplay.android.uikit.util.compose.visibilityBy
import com.gplay.app.R
import com.gplay.app.ui.GPlayScaffold
import com.gplay.app.ui.LocalScaffoldController
import com.gplay.app.ui.theme.GPlayTheme
import com.gplay.core.domain.validation.isAllValid
import kotlinx.coroutines.launch

@Composable
fun LoginView(
    uiState: LoginUiState,
    onSendEvent: (LoginUiEvent) -> Unit,
    onSigned: () -> Unit,
) {
    val scaffoldController = LocalScaffoldController.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    when {
        uiState.isSignedIn -> onSigned()
        uiState.error != null -> scope.launch {
            val result = scaffoldController.showSnackbar(
                message = uiState.error.message ?: context.getString(R.string.error_need_retry),
            )
            when (result) {
                SnackbarResult.Dismissed -> onSendEvent(LoginUiEvent.ClearError)
                else -> { /* no-op */ }
            }
        }
    }

    ConstraintLayout(Modifier.fillMaxSize()) {
        val topGuideline = createGuidelineFromTop(0.3f)
        val (
            loading,
            usernameTextField,
            passwordTextField,
            signInButton,
        ) = createRefs()

        LinearProgressIndicator(
            modifier = Modifier
                .constrainAs(loading) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .visibilityBy { uiState.isLoading }
                .semantics {
                    testTag = "Loading"
                },
        )

        UsernameTextField(
            value = uiState.username,
            onValueChange = { onSendEvent(LoginUiEvent.TypeUsername(it)) },
            modifier = Modifier.constrainAs(usernameTextField) {
                start.linkTo(parent.start)
                top.linkTo(topGuideline)
                end.linkTo(parent.end)
            },
            validation = uiState.validations[LoginFormSpec.Username.key],
        )

        PasswordTextField(
            value = uiState.password,
            onValueChange = { onSendEvent(LoginUiEvent.TypePassword(it)) },
            modifier = Modifier.constrainAs(passwordTextField) {
                start.linkTo(parent.start)
                top.linkTo(usernameTextField.bottom)
                end.linkTo(parent.end)
            },
            validation = uiState.validations[LoginFormSpec.Password.key],
        )

        Button(
            onClick = { onSendEvent(LoginUiEvent.SignIn) },
            modifier = Modifier
                .constrainAs(signInButton) {
                    top.linkTo(passwordTextField.bottom, margin = 8.dp)
                    end.linkTo(passwordTextField.end)
                }
                .semantics {
                    testTag = "SignInButton"
                },
            enabled = uiState.validations.isAllValid(),
        ) {
            Text(stringResource(id = R.string.button_signin))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    GPlayTheme {
        GPlayScaffold {
            LoginView(
                uiState = LoginUiState(),
                onSendEvent = { /* no-op */ },
                onSigned = { /* no-op */ },
            )
        }
    }
}
