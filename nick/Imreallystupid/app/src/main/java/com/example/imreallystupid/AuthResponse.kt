package com.example.imreallystupid

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: Data?
)

data class Data (
    @SerializedName("name") val name : String,
    @SerializedName("memberId") val memberId : Int,
    @SerializedName("accessToken") val accessToken : String,
    @SerializedName("result") val result : String
)