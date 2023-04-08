package com.github.lotaviods.linkfatec.di

import com.github.lotaviods.linkfatec.data.local.UserRepositoryLocal
import com.github.lotaviods.linkfatec.data.remote.client.LoginWebClient
import com.github.lotaviods.linkfatec.data.remote.repository.LoginRepositoryImpl
import com.github.lotaviods.linkfatec.data.repository.LoginRepository
import com.github.lotaviods.linkfatec.data.repository.UserRepository
import com.github.lotaviods.linkfatec.ui.login.viewmodel.LoginScreenViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val loginModules: List<Module> = listOf(
    module {
        viewModel { LoginScreenViewModel(get(), get()) }
        single<UserRepository> { UserRepositoryLocal(androidApplication()) }
        single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
        single { LoginWebClient() }
    }
)