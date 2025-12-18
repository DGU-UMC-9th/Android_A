package com.example.imreallystupid

import android.accounts.AccountAuthenticatorResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("signup")
    fun signUp(@Body user:User): Call<AuthResponse>

    @POST("login")
    fun login(@Body user:User): Call<AuthResponse>

//    @GET("test")
//    fun test(
//        @Header("Authorization") token: String,
//    ) : Response<AuthResponse>
}