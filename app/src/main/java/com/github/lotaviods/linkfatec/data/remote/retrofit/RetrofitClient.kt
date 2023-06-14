package com.github.lotaviods.linkfatec.data.remote.retrofit

import com.github.lotaviods.linkfatec.BuildConfig
import com.github.lotaviods.linkfatec.data.remote.interceptor.TokenInterceptor
import com.github.lotaviods.linkfatec.data.remote.model.Notification
import com.github.lotaviods.linkfatec.data.remote.serializer.NotificationSerializer
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class RetrofitClient(tokenInterceptor: TokenInterceptor) {

    val client: Retrofit =
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .readTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(2, TimeUnit.MINUTES)
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .build()
            )
            .baseUrl(API_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .registerTypeAdapter(Notification::class.java, NotificationSerializer())
                        .create(),
                )
            )
            .build();

    val clientAuth: Retrofit =
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .readTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(2, TimeUnit.MINUTES)
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .addInterceptor(tokenInterceptor)
                    .build()
            )
            .baseUrl(API_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .registerTypeAdapter(Notification::class.java, NotificationSerializer())
                        .create(),
                )
            )
            .build();


    inline fun <reified T> getServiceAuth(): T {
        return clientAuth.create()
    }

    inline fun <reified T> getService(): T {
        return client.create()
    }

    companion object {
        private const val API_URL: String = BuildConfig.LINK_BASE_URL
    }

}