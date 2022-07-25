package com.gplay.core.data.auth.local

import app.cash.sqldelight.db.SqlDriver
import com.gplay.core.data.auth.AuthDataSource
import com.gplay.core.data.db.AppDatabase
import com.gplay.core.util.DatabaseTest
import org.koin.test.get
import org.koin.test.inject
import kotlin.test.BeforeTest

class LocalAuthDataSourceTest : DatabaseTest {
    private val driver: SqlDriver by inject()
    private val database: AppDatabase by inject()
    private lateinit var dataSource: AuthDataSource

    @BeforeTest
    override fun setup() {
        super.setup()
        dataSource = LocalAuthDataSource(get())
    }
}
