package com.github.lotaviods.linkfatec.di

import com.github.lotaviods.linkfatec.data.remote.client.DeviceWebClient
import com.github.lotaviods.linkfatec.data.remote.repository.DeviceRepository
import com.github.lotaviods.linkfatec.ui.application.viewmodel.ApplicationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val mainModules: List<Module> = listOf(
    module {
        viewModel {
            ApplicationViewModel(get(), get())
        }
        single { DeviceWebClient() }
        single { DeviceRepository(get()) }
    }
)