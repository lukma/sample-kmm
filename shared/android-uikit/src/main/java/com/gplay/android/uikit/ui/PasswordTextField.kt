package com.gplay.android.uikit.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.unit.dp
import com.gplay.android.uikit.R
import com.gplay.android.uikit.util.validation.message
import com.gplay.core.domain.validation.ValidationState

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    validation: ValidationState? = null,
) {
    val context = LocalContext.current

    Column(modifier) {
        var passwordHidden by rememberSaveable { mutableStateOf(true) }
        val errorMessage by remember(value) {
            val text = (validation as? ValidationState.Invalid)
                ?.error
                ?.message(context, context.getString(R.string.textfield_password))
                ?: ""
            mutableStateOf(text)
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .onFocusChanged {
                    if (it.isCaptured && !it.isFocused && validation == null) {
                        onValueChange(value)
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
            isError = errorMessage.isNotEmpty(),
            visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
        )

        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(start = 16.dp, top = 4.dp)
                .alpha(if (errorMessage.isNotEmpty()) 1f else 0f)
                .semantics {
                    testTag = "passwordTextFieldError"
                },
        )
    }
}
