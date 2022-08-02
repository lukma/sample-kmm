package com.gplay.app.di

import com.gplay.app.feature.login.LoginViewModel
import com.gplay.app.main.MainViewModel
import com.gplay.app.util.AndroidKoinTest
import com.gplay.app.util.CoroutinesTestRule
import org.junit.Rule
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertNotNull

class ViewModelModuleTest : AndroidKoinTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `should inject MainViewModel`() {
        // when
        val component = get<MainViewModel>()

        // then
        assertNotNull(component)
    }

    @Test
    fun `should inject LoginViewModel`() {
        // when
        val component = get<LoginViewModel>()

        // then
        assertNotNull(component)
    }
}
