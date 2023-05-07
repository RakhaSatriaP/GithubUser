package com.example.submissionawalgithub

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object Api {
    private val okhttp = OkHttpClient.Builder().apply {


        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        addInterceptor(loggingInterceptor)
    }
        .readTimeout(10000, TimeUnit.MILLISECONDS).writeTimeout(100000, TimeUnit.MILLISECONDS)
        .connectTimeout(50000, TimeUnit.MILLISECONDS).build()

    private val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/")
        .client(okhttp).addConverterFactory(GsonConverterFactory.create()).build()

    val service = retrofit.create<Service>()

}