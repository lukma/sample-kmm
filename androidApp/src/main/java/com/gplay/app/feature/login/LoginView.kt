package com.gplay.app.feature.login

import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.gplay.app.R
import com.gplay.app.main.MainUiEvent
import com.gplay.app.ui.LocalScaffoldController
import com.gplay.app.ui.theme.GPlayTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginView(
    viewModel: LoginViewModel = getViewModel(),
    onSendMainUiEvent: (MainUiEvent) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val scaffoldController = LocalScaffoldController.current

    when {
        uiState.isSignedIn -> onSendMainUiEvent(MainUiEvent.CheckIsSignedIn)
        uiState.error != null -> {
            scaffoldController.showSnackbar(uiState.error?.message)
            viewModel.clearError()
        }
    }

    ConstraintLayout(Modifier.fillMaxSize()) {
        val topGuideline = createGuidelineFromTop(0.3f)
        val (
            usernameTextField,
            passwordTextField,
            signInButton,
        ) = createRefs()

        OutlinedTextField(
            value = uiState.username,
            onValueChange = viewModel::onUsernameChanged,
            modifier = Modifier
                .constrainAs(usernameTextField) {
                    start.linkTo(parent.start)
                    top.linkTo(topGuideline)
                    end.linkTo(parent.end)
                }
                .semantics {
                    testTag = "usernameTextField"
                },
            label = { Text(stringResource(id = R.string.textfield_username)) },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.AlternateEmail, contentDescription = null)
            },
        )

        var passwordHidden by rememberSaveable { mutableStateOf(true) }
        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::onPasswordChanged,
            modifier = Modifier
                .constrainAs(passwordTextField) {
                    start.linkTo(parent.start)
                    top.linkTo(usernameTextField.bottom, margin = 8.dp)
                    end.linkTo(parent.end)
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
            visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
        )

        Button(
            onClick = viewModel::signIn,
            modifier = Modifier
                .constrainAs(signInButton) {
                    top.linkTo(passwordTextField.bottom, margin = 16.dp)
                    end.linkTo(passwordTextField.end)
                }
                .semantics {
                    testTag = "signInButton"
                },
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
