package com.example.mission2

interface TestView {
    fun onTestSuccess(message: String)
    fun onTestFailure(message: String)
}