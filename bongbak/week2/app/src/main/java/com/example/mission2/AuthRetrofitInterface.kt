package com.example.mission2

import android.accounts.AccountAuthenticatorResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("signup")
    fun signUp(@Body user: User): Call<AuthResponse>
    @POST("login")
    fun login(@Body request: LoginRequest): Call<AuthResponse>

    @GET("/test")
    fun jwtTest(@Header("Authorization") token:String): Call<AuthResponse>
}