package com.github.lotaviods.linkfatec.di

import org.koin.core.module.Module

object AppModules {
    private val mModules = mutableListOf<Module>()
    val modules: List<Module> get() = mModules

    init {
        mModules.apply {
            addAll(loginModules)
            addAll(mainModules)
            addAll(opportunitiesModule)
        }
    }
}