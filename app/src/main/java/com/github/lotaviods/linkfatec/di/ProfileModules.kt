package com.github.lotaviods.linkfatec.di

import com.github.lotaviods.linkfatec.data.remote.client.ProfileWebClient
import com.github.lotaviods.linkfatec.data.remote.repository.ProfileRepositoryImpl
import com.github.lotaviods.linkfatec.data.repository.ProfileRepository
import com.github.lotaviods.linkfatec.ui.modules.profile.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val profileModules: List<Module> = listOf(
    module {
        single { ProfileWebClient() }
        single<ProfileRepository> { ProfileRepositoryImpl(get()) }
        viewModel {
            ProfileViewModel(get(), get())
        }
    }
)

