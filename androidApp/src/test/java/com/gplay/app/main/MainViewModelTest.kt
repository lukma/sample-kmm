package com.gplay.app.main

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
        viewModel.sendEvent(MainUiEvent.CheckIsSignedIn)
        val actual = viewModel.uiState.value

        // then
        val expected = MainUiState(isSignedIn = true)
        assertEquals(expected, actual)
    }

    @Test
    fun `perform get start destination got login screen`() {
        // given
        coEvery { isSignedInUseCase() } returns flow { emit(false) }

        // when
        viewModel.sendEvent(MainUiEvent.CheckIsSignedIn)
        val actual = viewModel.uiState.value

        // then
        val expected = MainUiState(isSignedIn = false)
        assertEquals(expected, actual)
    }
}
