package com.gplay.core.data.auth.network

import com.gplay.core.data.auth.AuthDataSource
import com.gplay.core.domain.common.exception.APIError
import com.gplay.core.util.HttpClientTest
import com.gplay.core.util.TestSamples
import io.ktor.client.*
import io.ktor.http.*
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.koin.test.get
import org.koin.test.inject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NetworkAuthDataSourceTest : HttpClientTest {
    private val apiClient: HttpClient by inject()
    private lateinit var dataSource: AuthDataSource

    @BeforeTest
    override fun setup() {
        super.setup()
        dataSource = NetworkAuthDataSource(get())
    }

    @Test
    fun `perform sign in got value`() = runTest {
        // given
        apiClient.mockResponse(
            body = """
                {
                    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImpvaG4uZG9lQG1haWwuY29tIiwiZnVsbG5hbWUiOiJKb2huIERvZSJ9.z5D0sySgc4zqI0zL1dakHVMYaYrPPtoAFQvZGKL7kTg",
                    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImpvaG4uZG9lQG1haWwuY29tIiwiZnVsbG5hbWUiOiJKb2huIERvZSJ9.z5D0sySgc4zqI0zL1dakHVMYaYrPPtoAFQvZGKL7kTg",
                    "token_type": "Bearer",
                    "expires_in": "2022-12-31T12:00:00.000124Z"
                }
                """,
            status = HttpStatusCode.OK,
        )

        // when
        val actual = dataSource.signIn(
            username = TestSamples.username,
            password = TestSamples.password,
        ).single()

        // then
        val expected = TestSamples.token
        assertEquals(expected, actual)
    }

    @Test
    fun `perform sign in got failure cause username not found`() = runTest {
        // given
        apiClient.mockResponse(
            body = """
                {
                    "error_code": "GE-201",
                    "error_description": "Some error"
                }
                """,
            status = HttpStatusCode.BadRequest,
        )

        // when
        val actual = runCatching {
            dataSource.signIn(
                username = TestSamples.username,
                password = TestSamples.password,
            ).single()
        }

        // then
        assertFailsWith<APIError.UsernameNotFound> { actual.getOrThrow() }
    }

    @Test
    fun `perform sign in got failure cause password missmatch`() = runTest {
        // given
        apiClient.mockResponse(
            body = """
                {
                    "error_code": "GE-202",
                    "error_description": "Some error"
                }
                """,
            status = HttpStatusCode.BadRequest,
        )

        // when
        val actual = runCatching {
            dataSource.signIn(
                username = TestSamples.username,
                password = TestSamples.password,
            ).single()
        }

        // then
        assertFailsWith<APIError.PasswordMissmatch> { actual.getOrThrow() }
    }

    @Test
    fun `perform sign in got failure cause account inactive`() = runTest {
        // given
        apiClient.mockResponse(
            body = """
                {
                    "error_code": "GE-301",
                    "error_description": "Some error"
                }
                """,
            status = HttpStatusCode.Forbidden,
        )

        // when
        val actual = runCatching {
            dataSource.signIn(
                username = TestSamples.username,
                password = TestSamples.password,
            ).single()
        }

        // then
        assertFailsWith<APIError.AccountInactive> { actual.getOrThrow() }
    }
}
