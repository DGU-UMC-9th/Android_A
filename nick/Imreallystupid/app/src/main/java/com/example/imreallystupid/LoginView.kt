package com.example.imreallystupid

interface LoginView {
    fun onLoginSuccess(code: String, data: Data)
    fun onLoginFailure()
}