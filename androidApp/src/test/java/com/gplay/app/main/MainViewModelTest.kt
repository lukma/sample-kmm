package com.gplay.app.main

import com.gplay.app.feature.home.HomeScreen
import com.gplay.app.feature.login.LoginScreen
import com.gplay.app.util.CoroutinesTestRule
import com.gplay.core.domain.auth.usecase.IsSignedInUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class MainViewModelTest {
    private val isSignedInUseCase: IsSignedInUseCase = mockk()
    private lateinit var viewModel: MainViewModel

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setup() {
        viewModel = MainViewModel(
            isSignedInUseCase,
        )
    }

    @Test
    fun `perform get start destination got home screen`() {
        // given
        coEvery { isSignedInUseCase() } returns flow { emit(true) }

        // when
        viewModel.getStartDestination()
        val actual = viewModel.startDestination.value

        // then
        val expected = HomeScreen.route
        assertEquals(expected, actual)
    }

    @Test
    fun `perform get start destination got login screen`() {
        // given
        coEvery { isSignedInUseCase() } returns flow { emit(false) }

        // when
        viewModel.getStartDestination()
        val actual = viewModel.startDestination.value

        // then
        val expected = LoginScreen.route
        assertEquals(expected, actual)
    }
}
