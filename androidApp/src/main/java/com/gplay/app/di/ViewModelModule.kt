package com.gplay.app.di

import com.gplay.app.feature.home.HomeViewModel
import com.gplay.app.feature.login.LoginViewModel
import com.gplay.app.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
}
