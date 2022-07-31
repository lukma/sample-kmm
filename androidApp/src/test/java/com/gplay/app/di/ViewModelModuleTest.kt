package com.gplay.app.di

import com.gplay.app.main.MainViewModel
import com.gplay.app.util.AutoInitKoinTest
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertNotNull

class ViewModelModuleTest : AutoInitKoinTest {

    @Test
    fun `should inject MainViewModel`() {
        // when
        val component = get<MainViewModel>()

        // then
        assertNotNull(component)
    }
}
