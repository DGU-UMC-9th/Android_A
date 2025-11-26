package com.rkdgudrn4094.week2

import com.google.gson.annotations.SerializedName

data class TestResponse(
    @SerializedName(value="status") val status: Boolean,
    @SerializedName(value="code") val code: String,
    @SerializedName(value="message") val message: String,
    @SerializedName(value="data") val data: TestData?
)

data class TestData(
    @SerializedName(value="result") val result: String
)
