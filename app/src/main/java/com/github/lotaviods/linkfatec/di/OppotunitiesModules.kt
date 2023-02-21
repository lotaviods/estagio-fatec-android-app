package com.github.lotaviods.linkfatec.di

import com.github.lotaviods.linkfatec.ui.modules.opportunities.viewmodel.OpportunitiesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val opportunitiesModule: List<Module> = listOf(
    module {
        viewModel { OpportunitiesViewModel(get(), get()) }
    }
)