package com.example.mission2

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName(value="status")val status:Boolean,
    @SerializedName(value="code")val code:String,
    @SerializedName(value="message")val message: String,
    @SerializedName(value="data") val result:Result?
)
data class Result(
    @SerializedName(value="name") val name:String?,
    @SerializedName(value="memberId") var memberId:Int,
    @SerializedName(value="accessToken") var jwt: String?
)
