package com.gplay.core.util

import com.gplay.core.initKoin
import io.mockk.mockk
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

interface AutoInitKoinTest : KoinTest {

    @BeforeTest
    fun setup() {
        initKoin(appConfig = mockk(relaxed = true))
        val testPlatformModule = module {
            single { createTestSqlDriver() }
        }
        loadKoinModules(testPlatformModule)
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }
}
