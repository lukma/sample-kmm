package com.gplay.core.data.article.network

import com.gplay.core.data.article.ArticleDataSource
import com.gplay.core.domain.common.exception.APIError
import com.gplay.core.util.HttpClientTest
import com.gplay.core.util.TestSamples
import io.ktor.client.*
import io.ktor.http.*
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.koin.test.get
import org.koin.test.inject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NetworkArticleDataSourceTest : HttpClientTest {
    private val apiClient: HttpClient by inject()
    private lateinit var dataSource: ArticleDataSource

    @BeforeTest
    override fun setup() {
        super.setup()
        dataSource = NetworkArticleDataSource(get())
    }

    @Test
    fun `perform get articles got value`() = runTest {
        // given
        apiClient.mockResponse(
            body = """
                {
                    "items": [{
                        "id": "1",
                        "title": "Lorem ipsum",
                        "content": "Lorem ipsum dolor sit amet",
                        "thumbnail": "https://picsum.photos/id/1/300",
                        "created_at": "2022-12-31T12:00:00.000124Z"
                    }],
                    "total": 1
                }
                """,
            status = HttpStatusCode.OK,
        )

        // when
        val actual = dataSource.getArticles(paging = TestSamples.pagingParams)
            .single()

        // then
        val expected = TestSamples.articlePagingFromNetwork
        assertEquals(expected, actual)
    }

    @Test
    fun `perform get articles got failure cause invalid token`() = runTest {
        // given
        apiClient.mockResponse(
            body = """
                {
                    "error_code": "GE-101",
                    "error_description": "Some error"
                }
                """,
            status = HttpStatusCode.Unauthorized,
        )

        // when
        val actual = runCatching {
            dataSource.getArticles(paging = TestSamples.pagingParams)
                .single()
        }

        // then
        assertFailsWith<APIError.InvalidToken> { actual.getOrThrow() }
    }
}
