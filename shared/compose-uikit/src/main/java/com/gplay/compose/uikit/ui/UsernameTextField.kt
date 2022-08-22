package com.gplay.compose.uikit.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.gplay.android.uikit.R
import com.gplay.compose.uikit.util.compose.visibilityBy
import com.gplay.compose.uikit.util.validation.errorMessage
import com.gplay.core.domain.validation.ValidationState

@Composable
fun UsernameTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    validation: ValidationState? = null,
) {
    val context = LocalContext.current

    Column(modifier) {
        val errorMessage by remember(value) {
            val text = (validation as? ValidationState.Invalid)
                ?.errorMessage(context, context.getString(R.string.textfield_username))
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
                    testTag = "UsernameTextField"
                },
            label = { Text(stringResource(id = R.string.textfield_username)) },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.AlternateEmail, contentDescription = null)
            },
            isError = errorMessage.isNotEmpty(),
        )

        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(start = 16.dp, top = 4.dp)
                .visibilityBy { errorMessage.isNotEmpty() }
                .semantics {
                    testTag = "UsernameTextFieldError"
                },
        )
    }
}
