package com.gplay.core.di

import com.gplay.core.util.AutoInitKoinTest
import io.ktor.client.*
import org.koin.core.qualifier.named
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class NetworkModuleTest : AutoInitKoinTest {

    @Test
    fun `should inject Simple HttpClient`() {
        // when
        val component1 = get<HttpClient>(named(HttpClientQualifier.Simple))
        val component2 = get<HttpClient>(named(HttpClientQualifier.Simple))

        // then
        assertNotNull(component1)
        assertEquals(component1, component2)
    }
}
