package com.github.lotaviods.linkfatec.di

import com.github.lotaviods.linkfatec.data.remote.client.JobOfferWebClient
import com.github.lotaviods.linkfatec.data.remote.interceptor.TokenInterceptor
import com.github.lotaviods.linkfatec.data.remote.repository.JobOfferRepositoryImpl
import com.github.lotaviods.linkfatec.data.repository.interfaces.JobOfferRepository
import com.github.lotaviods.linkfatec.data.remote.retrofit.RetrofitClient
import org.koin.core.module.Module
import org.koin.dsl.module

val httpModules: List<Module> = listOf(
    module {
        single { RetrofitClient(get()) }
        single { JobOfferWebClient() }
        single<JobOfferRepository> { JobOfferRepositoryImpl(get()) }
        single { TokenInterceptor(get()) }
    }
)