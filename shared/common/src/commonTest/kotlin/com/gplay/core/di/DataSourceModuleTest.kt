package com.gplay.core.di

import com.gplay.core.data.article.ArticleDataSource
import com.gplay.core.data.auth.AuthDataSource
import com.gplay.core.util.AutoInitKoinTest
import org.koin.core.qualifier.named
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class DataSourceModuleTest : AutoInitKoinTest {

    @Test
    fun `should inject Local AuthDataSource`() {
        // when
        val component1 = get<AuthDataSource>(named(DataSourceQualifier.Local))
        val component2 = get<AuthDataSource>(named(DataSourceQualifier.Local))

        // then
        assertNotNull(component1)
        assertNotEquals(component1, component2)
    }

    @Test
    fun `should inject Network AuthDataSource`() {
        // when
        val component1 = get<AuthDataSource>(named(DataSourceQualifier.Network))
        val component2 = get<AuthDataSource>(named(DataSourceQualifier.Network))

        // then
        assertNotNull(component1)
        assertNotEquals(component1, component2)
    }

    @Test
    fun `should inject Local ArticleDataSource`() {
        // when
        val component1 = get<ArticleDataSource>(named(DataSourceQualifier.Local))
        val component2 = get<ArticleDataSource>(named(DataSourceQualifier.Local))

        // then
        assertNotNull(component1)
        assertNotEquals(component1, component2)
    }

    @Test
    fun `should inject Network ArticleDataSource`() {
        // when
        val component1 = get<ArticleDataSource>(named(DataSourceQualifier.Network))
        val component2 = get<ArticleDataSource>(named(DataSourceQualifier.Network))

        // then
        assertNotNull(component1)
        assertNotEquals(component1, component2)
    }
}
