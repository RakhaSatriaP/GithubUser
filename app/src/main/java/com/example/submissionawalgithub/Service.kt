package com.example.submissionawalgithub

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface Service {
    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUser(@Header("Authorization")authorization:String = BuildConfig.TOKEN) : MutableList<Response.Item>

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun getSearchUser(@QueryMap params : Map<String,Any>,@Header("Authorization")authorization:String = BuildConfig.TOKEN) : Response

    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username") username:String,@Header("Authorization")authorization:String = BuildConfig.TOKEN) : DetailUser

    @JvmSuppressWildcards
    @GET("users/{username}/followers")
    suspend fun getFollowersUser(@Path("username") username:String,@Header("Authorization")authorization:String = BuildConfig.TOKEN,) : MutableList<Response.Item>

    @JvmSuppressWildcards
    @GET("users/{username}/following")
    suspend fun getFollowingsUser(@Path("username") username:String,@Header("Authorization")authorization:String = BuildConfig.TOKEN,) : MutableList<Response.Item>




}