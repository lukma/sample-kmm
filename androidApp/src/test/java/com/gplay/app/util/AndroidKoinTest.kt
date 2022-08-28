package com.gplay.app.util

import com.gplay.app.di.viewModelModule
import com.gplay.core.initKoin
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

interface AndroidKoinTest : KoinTest {

    @Before
    fun setup() {
        initKoin(appConfig = mockk(relaxed = true)) {
            androidContext(mockk(relaxed = true))
        }
        loadKoinModules(
            listOf(
                viewModelModule,
            )
        )
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}
