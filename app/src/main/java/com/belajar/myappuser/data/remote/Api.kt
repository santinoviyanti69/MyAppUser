package com.belajar.myappuser.data.remote

import com.afollestad.materialdialogs.BuildConfig
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

val BASE_URL = "https://api.github.com/"

object Api {
    private fun createRetrofit(
        httpClient: OkHttpClient.Builder,
        baseUrl: String = BASE_URL
    ): Retrofit {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        httpClient
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer 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").build()
                chain.proceed(request)
            }

        if(BuildConfig.DEBUG){
            httpClient
                .addInterceptor(loggingInterceptor)
        }

        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    fun <ServiceClass> createService(
        serviceClass: Class<ServiceClass>
    ): ServiceClass {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(120, TimeUnit.SECONDS)
        httpClient.readTimeout(120, TimeUnit.SECONDS)
        httpClient.writeTimeout(120, TimeUnit.SECONDS)
        val retrofit = createRetrofit(httpClient)
        return retrofit.create(serviceClass)
    }

}