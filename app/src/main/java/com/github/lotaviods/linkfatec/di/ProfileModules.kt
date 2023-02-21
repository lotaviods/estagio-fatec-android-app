package com.github.lotaviods.linkfatec.di

import com.github.lotaviods.linkfatec.ui.modules.profile.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val profileModules: List<Module> = listOf(
    module {
        viewModel {
            ProfileViewModel(get())
        }
    }
)

