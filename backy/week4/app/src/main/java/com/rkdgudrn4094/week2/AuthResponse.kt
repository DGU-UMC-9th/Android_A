package com.rkdgudrn4094.week2

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName(value="status") val status: Boolean,
    @SerializedName(value="code") val code: String,
    @SerializedName(value="message") val message: String,
    @SerializedName(value="data") val data: LoginData?
)

data class LoginData(
    @SerializedName(value="name") val name: String,
    @SerializedName(value="memberId") val memberId: Int,
    @SerializedName(value="accessToken") var accessToken: String
)
