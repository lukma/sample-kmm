package com.gplay.core.data.article.local

import app.cash.sqldelight.db.SqlDriver
import com.gplay.core.data.article.ArticleDataSource
import com.gplay.core.data.db.AppDatabase
import com.gplay.core.domain.common.entity.asLocalLimitAndOffset
import com.gplay.core.domain.common.exception.APIError
import com.gplay.core.util.DatabaseTest
import com.gplay.core.util.TestSamples
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.koin.test.inject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LocalArticleDataSourceTest : DatabaseTest {
    private val driver: SqlDriver by inject()
    private val database: AppDatabase by inject()
    private lateinit var dataSource: ArticleDataSource

    @BeforeTest
    override fun setup() {
        super.setup()
        dataSource = LocalArticleDataSource(database)
    }

    @Test
    fun `perform store articles got success`() = runTest {
        // when
        dataSource.storeArticles(articles = TestSamples.articles)
        val (limit, offset) = TestSamples.pagingParams.asLocalLimitAndOffset()
        val actual = database.articleQueries
            .finds(limit, offset, ::mapToArticle)
            .executeAsList()

        // then
        val expected = TestSamples.articles
        assertEquals(expected, actual)
    }

    @Test
    fun `perform store articles got failure`() = runTest {
        // given
        driver.close()

        // when
        val actual = runCatching {
            dataSource.storeArticles(articles = TestSamples.articles)
        }

        // then
        assertFailsWith<APIError.DatabaseFailure> { actual.getOrThrow() }
    }

    @Test
    fun `perform get articles got value`() = runTest {
        // given
        database.articleQueries
            .upsert(articleTable = TestSamples.articles.first().toArticleTable())

        // when
        val actual = dataSource.getArticles(paging = TestSamples.pagingParams)
            .single()

        // then
        val expected = TestSamples.articlePagingFromLocal
        assertEquals(expected, actual)
    }

    @Test
    fun `perform get articles got failure`() = runTest {
        // given
        driver.close()

        // when
        val actual = runCatching {
            dataSource.getArticles(paging = TestSamples.pagingParams)
                .single()
        }

        // then
        assertFailsWith<APIError.DatabaseFailure> { actual.getOrThrow() }
    }
}
