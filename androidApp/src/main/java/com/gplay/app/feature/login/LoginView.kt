package com.gplay.app.feature.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.gplay.app.R
import com.gplay.app.main.MainUiEvent
import com.gplay.app.ui.LocalScaffoldController
import com.gplay.app.ui.theme.GPlayTheme
import com.gplay.app.util.validation.message
import com.gplay.core.domain.validation.ValidationState
import com.gplay.core.domain.validation.isAllValid
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginView(
    viewModel: LoginViewModel = getViewModel(),
    onSendMainUiEvent: (MainUiEvent) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val scaffoldController = LocalScaffoldController.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    when {
        uiState.isSignedIn -> onSendMainUiEvent(MainUiEvent.CheckIsSignedIn)
        uiState.error != null -> scope.launch {
            when (scaffoldController.showSnackbar(uiState.error?.message)) {
                SnackbarResult.Dismissed -> viewModel.sendEvent(LoginUiEvent.ClearError)
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
                .alpha(if (uiState.isLoading) 1f else 0f)
                .semantics {
                    testTag = "loading"
                },
        )

        Column(Modifier.constrainAs(usernameTextField) {
            start.linkTo(parent.start)
            top.linkTo(topGuideline)
            end.linkTo(parent.end)
        }) {
            val error by remember(uiState.username) {
                val text = (uiState.validations[LoginFormSpec.Username] as? ValidationState.Invalid)
                    ?.error
                    ?.message(context, context.getString(R.string.textfield_username))
                    ?: ""
                mutableStateOf(text)
            }

            OutlinedTextField(
                value = uiState.username,
                onValueChange = { viewModel.sendEvent(LoginUiEvent.TypeUsername(it)) },
                modifier = Modifier
                    .onFocusChanged {
                        if (it.isCaptured && !it.isFocused && uiState.validations[LoginFormSpec.Username] == null) {
                            viewModel.sendEvent(LoginUiEvent.TypeUsername(uiState.username))
                        }
                    }
                    .semantics {
                        testTag = "usernameTextField"
                    },
                label = { Text(stringResource(id = R.string.textfield_username)) },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.AlternateEmail, contentDescription = null)
                },
                isError = error.isNotEmpty(),
            )

            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(start = 16.dp, top = 4.dp)
                    .alpha(if (error.isNotEmpty()) 1f else 0f)
                    .semantics {
                        testTag = "usernameTextFieldError"
                    },
            )
        }

        Column(Modifier.constrainAs(passwordTextField) {
            start.linkTo(parent.start)
            top.linkTo(usernameTextField.bottom)
            end.linkTo(parent.end)
        }) {
            var passwordHidden by rememberSaveable { mutableStateOf(true) }
            val error by remember(uiState.password) {
                val text = (uiState.validations[LoginFormSpec.Password] as? ValidationState.Invalid)
                    ?.error
                    ?.message(context, context.getString(R.string.textfield_password))
                    ?: ""
                mutableStateOf(text)
            }

            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.sendEvent(LoginUiEvent.TypePassword(it)) },
                modifier = Modifier
                    .onFocusChanged {
                        if (it.isCaptured && !it.isFocused && uiState.validations[LoginFormSpec.Password] == null) {
                            viewModel.sendEvent(LoginUiEvent.TypePassword(uiState.password))
                        }
                    }
                    .semantics {
                        testTag = "passwordTextField"
                    },
                label = { Text(stringResource(id = R.string.textfield_password)) },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Lock, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = { passwordHidden = !passwordHidden }) {
                        val visibilityIcon =
                            if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        Icon(imageVector = visibilityIcon, contentDescription = null)
                    }
                },
                isError = error.isNotEmpty(),
                visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
            )

            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(start = 16.dp, top = 4.dp)
                    .alpha(if (error.isNotEmpty()) 1f else 0f)
                    .semantics {
                        testTag = "passwordTextFieldError"
                    },
            )
        }

        Button(
            onClick = { viewModel.sendEvent(LoginUiEvent.SignIn) },
            modifier = Modifier
                .constrainAs(signInButton) {
                    top.linkTo(passwordTextField.bottom, margin = 8.dp)
                    end.linkTo(passwordTextField.end)
                }
                .semantics {
                    testTag = "signInButton"
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
        LoginView(
            onSendMainUiEvent = { /* no-op */ },
        )
    }
}
