package com.gplay.compose.uikit.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.gplay.compose.uikit.R

@Composable
fun ErrorView(
    error: Throwable,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card {
        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
                .semantics {
                    testTag = "ErrorView"
                },
        ) {
            val (illustration, message, retryButton) = createRefs()

            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.common_error))
            LottieAnimation(
                composition = composition,
                modifier = Modifier.constrainAs(illustration) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    height = Dimension.value(120.dp)
                },
                iterations = LottieConstants.IterateForever,
            )

            Text(
                text = error.message ?: stringResource(id = R.string.error_need_retry),
                modifier = Modifier.constrainAs(message) {
                    start.linkTo(parent.start)
                    top.linkTo(illustration.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
            )

            Button(
                onClick = onRetry,
                modifier = Modifier.constrainAs(retryButton) {
                    start.linkTo(parent.start)
                    top.linkTo(message.bottom, margin = 8.dp)
                    end.linkTo(parent.end)
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp),
                )
                Text(text = stringResource(id = R.string.button_retry))
            }
        }
    }
}
