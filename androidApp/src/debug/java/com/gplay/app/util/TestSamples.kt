package com.gplay.app.util

import com.gplay.core.domain.article.Article
import com.gplay.core.domain.common.entity.PagingResult
import kotlinx.datetime.Instant

object TestSamples {
    // Common
    val error = Error("some error")

    // Auth
    const val username = "john.doe@mail.com"
    const val password = "qwerty"

    // Article
    fun articlePagingResult(page: Int = 1): PagingResult<Article> {
        val offset = (page * 10) - 9
        val articles = (offset..offset + 10).map {
            Article(
                id = it.toString(),
                title = "$it. Lorem ipsum",
                content = "<p>$it. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis molestie orci at scelerisque. Vestibulum finibus iaculis pellentesque. Phasellus ornare commodo purus in sagittis. In sodales pretium orci non facilisis. Ut molestie egestas neque, in viverra arcu pellentesque sit amet. Aliquam auctor turpis ac sagittis tincidunt. Vivamus sed elit vitae risus ultricies dapibus sodales ut libero. Fusce ultricies mauris mauris, et congue ipsum posuere ut. Vestibulum facilisis felis felis, in faucibus nibh tincidunt vel. Duis posuere consectetur bibendum. Suspendisse nunc lacus, elementum id congue vitae, ultricies aliquet massa. Suspendisse suscipit non mauris eget tincidunt. Suspendisse eu erat at metus rutrum scelerisque. Aenean ligula nisl, mattis a erat sit amet, ullamcorper auctor tellus.</p>",
                thumbnail = "https://picsum.photos/id/11/700",
                createdAt = Instant.parse("2022-12-31T12:00:00.000124Z"),
            )
        }
        return PagingResult.Network(items = articles, total = articles.size)
    }
}
