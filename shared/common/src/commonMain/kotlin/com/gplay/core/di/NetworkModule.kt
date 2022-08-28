package com.gplay.core.di

import com.gplay.core.config.AppConfig
import com.gplay.core.data.common.network.http.SimpleHttpClient
import io.ktor.client.plugins.*
import io.ktor.http.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val networkModule = module {
    single(named(HttpClientQualifier.Simple)) {
        SimpleHttpClient(get()) {
            defaultRequest {
                host = get<AppConfig>().apiHost
                url.protocol = URLProtocol.HTTPS
                url.encodedPath = get<AppConfig>().apiPrefixPath
            }
        }
    }
}
