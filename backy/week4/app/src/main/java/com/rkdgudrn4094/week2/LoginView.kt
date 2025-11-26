package com.rkdgudrn4094.week2

interface LoginView {
    fun onLoginSuccess(code: String, data: LoginData)
    fun onLoginFailure()
}