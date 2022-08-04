package com.gplay.core.di

import com.gplay.core.domain.article.ArticleRepository
import com.gplay.core.domain.auth.AuthRepository
import com.gplay.core.util.AutoInitKoinTest
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class RepositoryModuleTest : AutoInitKoinTest {

    @Test
    fun `should inject AuthRepository`() {
        // when
        val component1 = get<AuthRepository>()
        val component2 = get<AuthRepository>()

        // then
        assertNotNull(component1)
        assertEquals(component1, component2)
    }

    @Test
    fun `should inject ArticleRepository`() {
        // when
        val component1 = get<ArticleRepository>()
        val component2 = get<ArticleRepository>()

        // then
        assertNotNull(component1)
        assertEquals(component1, component2)
    }
}
