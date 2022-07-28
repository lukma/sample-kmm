package com.gplay.core.data.auth.local

import com.gplay.core.data.auth.AuthDataSource
import com.gplay.core.data.db.AppDatabase
import com.gplay.core.domain.auth.Token
import com.gplay.core.domain.common.exception.APIError
import kotlinx.coroutines.flow.Flow

internal class LocalAuthDataSource(private val database: AppDatabase) : AuthDataSource {
    override suspend fun signIn(username: String, password: String): Flow<Token> {
        throw NotImplementedError()
    }

    override suspend fun storeToken(username: String, token: Token) {
        try {
            database.transaction {
                with(database.authQueries) {
                    deactiveCurrent()
                    upsert(auth = token.toLocalActiveAuth(username))
                }
            }
        } catch (ex: Exception) {
            throw APIError.DatabaseFailure(ex)
        }
    }

    override suspend fun isSignedIn(): Flow<Boolean> {
        throw NotImplementedError()
    }
}
