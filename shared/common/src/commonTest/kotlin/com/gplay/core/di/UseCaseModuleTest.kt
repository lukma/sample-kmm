package com.gplay.core.di

import com.gplay.core.domain.auth.usecase.IsSignedInUseCase
import com.gplay.core.domain.auth.usecase.SignInUseCase
import com.gplay.core.domain.validation.usecase.FormValidationUseCase
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

    @Test
    fun `should inject IsSignedInUseCase`() {
        // when
        val component1 = get<IsSignedInUseCase>()
        val component2 = get<IsSignedInUseCase>()

        // then
        assertNotNull(component1)
        assertNotNull(component2)
        assertNotEquals(component1, component2)
    }

    @Test
    fun `should inject FormValidationUseCase`() {
        // when
        val component1 = get<FormValidationUseCase>()
        val component2 = get<FormValidationUseCase>()

        // then
        assertNotNull(component1)
        assertNotNull(component2)
        assertNotEquals(component1, component2)
    }
}
