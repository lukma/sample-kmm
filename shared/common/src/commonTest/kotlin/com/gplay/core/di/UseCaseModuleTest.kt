package com.gplay.core.di

import com.gplay.core.domain.auth.usecase.SignInUseCase
import com.gplay.core.util.AutoInitKoinTest
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class UseCaseModuleTest : AutoInitKoinTest {

    @Test
    fun `should inject SignInUseCase`() {
        // when
        val component1 = get<SignInUseCase>()
        val component2 = get<SignInUseCase>()

        // then
        assertNotNull(component1)
        assertNotNull(component2)
        assertNotEquals(component1, component2)
    }
}
