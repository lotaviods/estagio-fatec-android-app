package com.github.lotaviods.linkfatec.di

import com.github.lotaviods.linkfatec.ui.modules.notifications.viewmodel.AppNotificationsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appNotificationModules: List<Module> = listOf(
    module {
        viewModel { AppNotificationsViewModel() }
    }
)