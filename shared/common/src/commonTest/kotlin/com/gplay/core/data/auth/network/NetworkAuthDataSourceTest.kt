package com.gplay.core.data.auth.network

import com.gplay.core.data.auth.AuthDataSource
import com.gplay.core.util.HttpClientTest
import io.ktor.client.*
import org.koin.test.get
import org.koin.test.inject
import kotlin.test.BeforeTest

class NetworkAuthDataSourceTest : HttpClientTest {
    private val apiClient: HttpClient by inject()
    private lateinit var dataSource: AuthDataSource

    @BeforeTest
    override fun setup() {
        super.setup()
        dataSource = NetworkAuthDataSource(get())
    }
}
