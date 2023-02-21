package com.github.lotaviods.linkfatec.data.retrofit

import com.github.lotaviods.linkfatec.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitClient {

    val client: Retrofit =
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build()
            )
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    inline fun <reified T> getService(): T {
        return client.create()
    }

    companion object {
        private const val API_URL: String = BuildConfig.LINK_BASE_URL
    }

}