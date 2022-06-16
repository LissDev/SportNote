package ru.lissdev.sportnote.data.network.core

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass

class Client<T : Any>(
    private val baseUrl: String,
    private val tokenKey: String,
    private val token: String,
    apiCls: KClass<T>
) {

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader(tokenKey, token)
                    .build()
                chain.proceed(request)
            })
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    internal val api by lazy { retrofit.create(apiCls.java) }
}