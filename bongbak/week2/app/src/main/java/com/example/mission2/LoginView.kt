package com.example.mission2

interface LoginView {
    fun onLoginSuccess(code: String, result: Result)
    fun onLoginFailure()
}