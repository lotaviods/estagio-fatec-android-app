package com.github.lotaviods.linkfatec.di

import com.github.lotaviods.linkfatec.data.repository.UserRepositoryImpl
import com.github.lotaviods.linkfatec.data.remote.client.LoginWebClient
import com.github.lotaviods.linkfatec.data.remote.repository.LoginRepositoryImpl
import com.github.lotaviods.linkfatec.data.repository.interfaces.LoginRepository
import com.github.lotaviods.linkfatec.data.repository.interfaces.UserRepository
import com.github.lotaviods.linkfatec.ui.login.viewmodel.LoginScreenViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val loginModules: List<Module> = listOf(
    module {
        viewModel { LoginScreenViewModel(get(), get()) }
        single<UserRepository> { UserRepositoryImpl(androidApplication(), get()) }
        single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
        single { LoginWebClient() }
    }
)