package com.gplay.core.data.auth.local

import app.cash.sqldelight.db.SqlDriver
import com.gplay.core.data.auth.AuthDataSource
import com.gplay.core.data.db.AppDatabase
import com.gplay.core.domain.common.exception.APIError
import com.gplay.core.util.DatabaseTest
import com.gplay.core.util.TestSamples
import kotlinx.coroutines.test.runTest
import org.koin.test.get
import org.koin.test.inject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LocalAuthDataSourceTest : DatabaseTest {
    private val driver: SqlDriver by inject()
    private val database: AppDatabase by inject()
    private lateinit var dataSource: AuthDataSource

    @BeforeTest
    override fun setup() {
        super.setup()
        dataSource = LocalAuthDataSource(get())
    }

    @Test
    fun `perform store token when initial store got success`() = runTest {
        // when
        dataSource.storeToken(username = TestSamples.username, token = TestSamples.token)
        val actual = database.authQueries
            .findByCurrentActive(::mapToToken)
            .executeAsOne()

        // then
        val expected = TestSamples.token
        assertEquals(expected, actual)
    }

    @Test
    fun `perform store token when next store got success`() = runTest {
        // given
        database.authQueries
            .upsert(auth = TestSamples.token.toLocalActiveAuth(username = "bob@mail.com"))

        // when
        dataSource.storeToken(username = TestSamples.username, token = TestSamples.token)
        val actual = database.authQueries
            .findByCurrentActive(::mapToToken)
            .executeAsOne()

        // then
        val expected = TestSamples.token
        assertEquals(expected, actual)
    }

    @Test
    fun `perform store token got failure`() = runTest {
        // given
        driver.close()

        // when
        val actual = runCatching {
            dataSource.storeToken(username = TestSamples.username, token = TestSamples.token)
        }

        // then
        assertFailsWith<APIError.DatabaseFailure> { actual.getOrThrow() }
    }
}
