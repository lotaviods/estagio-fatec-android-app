package com.github.lotaviods.linkfatec.di

import com.github.lotaviods.linkfatec.data.fake.LoginRepositoryFake
import com.github.lotaviods.linkfatec.data.local.UserRepositoryImpl
import com.github.lotaviods.linkfatec.data.repository.LoginRepository
import com.github.lotaviods.linkfatec.data.repository.UserRepository
import com.github.lotaviods.linkfatec.ui.login.viewmodel.LoginScreenViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val loginModules: List<Module> = listOf(
    module {
        viewModel { LoginScreenViewModel(get()) }
        single<LoginRepository> { LoginRepositoryFake() }
        single<UserRepository>{ UserRepositoryImpl(androidApplication()) }
//        viewModelOf(::LoginFormViewModel)
//        single<LoginRepository> { LoginRepositoryFake() }
    }
)