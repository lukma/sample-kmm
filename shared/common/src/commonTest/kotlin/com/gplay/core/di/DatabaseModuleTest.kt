package com.gplay.core.di

import com.gplay.core.data.db.AppDatabase
import com.gplay.core.util.AutoInitKoinTest
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DatabaseModuleTest : AutoInitKoinTest {

    @Test
    fun `should inject AppDatabase`() {
        // when
        val component1 = get<AppDatabase>()
        val component2 = get<AppDatabase>()

        // then
        assertNotNull(component1)
        assertEquals(component1, component2)
    }
}
