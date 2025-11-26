package com.rkdgudrn4094.week2

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView
    private lateinit var testView: TestView

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }
    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }
    fun setTestView(testView: TestView){
        this.testView = testView
    }

    fun signUp(user: User){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.signUp(user).enqueue(object: Callback<AuthResponse>{
            override fun onResponse(
                call: Call<AuthResponse?>,
                response: Response<AuthResponse?>
            ) {
                Log.d("SIGNUP/SUCCESS", response.toString())

                val resp: AuthResponse = response.body()!!

                if(::signUpView.isInitialized){
                    when(resp.code){
                        "COMMON201"->signUpView.onSignUpSuccess()
                        else->signUpView.onSignUpFailure()
                    }
                }

            }

            override fun onFailure(
                call: Call<AuthResponse?>,
                t: Throwable
            ) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }


        })

        Log.d("SIGNUP", "HELLOOO")
    }

    fun login(user: User){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(user).enqueue(object: Callback<AuthResponse>{
            override fun onResponse(
                call: Call<AuthResponse?>,
                response: Response<AuthResponse?>
            ) {
                Log.d("LOGIN/SUCCESS", response.toString())

                val resp: AuthResponse = response.body()!!
                val code = resp.code
                if(::loginView.isInitialized){
                    when(code){
                        "COMMON200_1"->loginView.onLoginSuccess(code, resp.data!!)
                        else->loginView.onLoginFailure()
                    }
                }

            }

            override fun onFailure(
                call: Call<AuthResponse?>,
                t: Throwable
            ) {
                Log.d("LOGIN/FAILURE", t.message.toString())
            }
        })

        Log.d("LOGIN", "HELLOOO")
    }

    fun test(token: String){
        val bearerToken = if (token.startsWith("Bearer ")) token else "Bearer $token"

        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.test(bearerToken).enqueue(object: Callback<TestResponse>{
            override fun onResponse(
                call: Call<TestResponse?>,
                response: Response<TestResponse?>
            ) {
                Log.d("TEST/SUCCESS", response.toString())

                val resp: TestResponse = response.body()!!
                val code = resp.code
                if(::testView.isInitialized){
                    when(code){
                        "COMMON200_1"->testView.onTestSuccess(resp.message)
                        else->testView.onTestFailure()
                    }
                }
            }

            override fun onFailure(
                call: Call<TestResponse?>,
                t: Throwable
            ) {
                Log.d("TEST/FAILURE", t.message.toString())
            }

        })

        Log.d("LOGIN", "HELLOOO")
    }
}