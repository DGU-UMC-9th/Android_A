package com.example.flo

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/signup")
    fun signUp(@Body user: User): Call<AuthResponse<SignUpResult>>

    @POST("/login")
    fun login(@Body user: User): Call<AuthResponse<LoginResult>>

    @GET("/test")
    fun test(@Header("Authorization") token: String): Call<AuthResponse<TestResult>>
}