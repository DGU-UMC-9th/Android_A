package com.example.flo

import com.google.gson.annotations.SerializedName

data class AuthResponse<T>(
    @SerializedName("status") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val result: T?
)

data class SignUpResult(
    @SerializedName("memberId") val memberId: Int
)

data class LoginResult(
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("name") val name: String
)

data class TestResult(
    @SerializedName("result") val result: String
)