package com.gplay.app.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.text.HtmlCompat
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.gplay.core.domain.article.Article

@Composable
fun ArticleItem(
    article: Article,
    isLoading: Boolean = false,
) {
    Card {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    testTag = "ArticleItem"
                },
        ) {
            val (thumbnail, title, content) = createRefs()

            val painter = rememberAsyncImagePainter(if (!isLoading) article.thumbnail else "")
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(thumbnail) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.ratio("16:9")
                    }
                    .shimmerPlaceholder(isLoading, RectangleShape),
            )

            Text(
                text = if (!isLoading) article.title else "",
                modifier = Modifier
                    .constrainAs(title) {
                        start.linkTo(parent.start, margin = 8.dp)
                        top.linkTo(thumbnail.bottom, margin = 8.dp)
                        width = if (!isLoading) {
                            end.linkTo(parent.end, margin = 8.dp)
                            Dimension.fillToConstraints
                        } else {
                            Dimension.percent(0.3f)
                        }
                    }
                    .shimmerPlaceholder(isLoading),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                text = if (!isLoading)
                    HtmlCompat.fromHtml(article.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
                        .toString()
                else "",
                modifier = Modifier
                    .constrainAs(content) {
                        start.linkTo(title.start)
                        top.linkTo(title.bottom, margin = 4.dp)
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        width = if (!isLoading) {
                            end.linkTo(title.end)
                            Dimension.fillToConstraints
                        } else {
                            Dimension.percent(0.5f)
                        }
                    }
                    .shimmerPlaceholder(isLoading),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = MaterialTheme.typography.titleSmall,
            )
        }
    }
}

@Composable
private fun Modifier.shimmerPlaceholder(
    isLoading: Boolean,
    shape: Shape = RoundedCornerShape(size = 8.dp),
): Modifier {
    val color = CardDefaults.cardColors().contentColor(enabled = true).value
    return placeholder(
        visible = isLoading,
        color = color.copy(alpha = .4f),
        shape = shape,
        highlight = PlaceholderHighlight.shimmer(color.copy(alpha = .5f)),
    )
}
