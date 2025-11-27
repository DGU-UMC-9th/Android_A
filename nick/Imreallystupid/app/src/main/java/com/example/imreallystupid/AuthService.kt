package com.example.imreallystupid

import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    fun setSignUpView(signUpView: SignUpView) {
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun signUp(user:User) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.signUp(user).enqueue(object : retrofit2.Callback<AuthResponse>{
            override fun onResponse(
                call: Call<AuthResponse?>,
                response: Response<AuthResponse?>
            ) {
                Log.d("SIGNUP/SUCCESS",response.toString())
                val resp: AuthResponse? = if (response.isSuccessful) {
                    response.body()
                } else {
                    Gson().fromJson(response.errorBody()?.string(), AuthResponse::class.java)
                }

                when(resp?.code?.trim()) {
                    "COMMON201" -> signUpView.onSignUpSuccess()
                    else -> signUpView.onSignUpFailure()
                }
            }

            override fun onFailure(
                call: Call<AuthResponse?>,
                t: Throwable
            ) {
                Log.d("SIGNUP/FAILURE",t.message.toString())
            }

        })
        Log.d("SIGNUP","HELLO")
    }

    fun login(user:User) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(user).enqueue(object : retrofit2.Callback<AuthResponse>{
            override fun onResponse(
                call: Call<AuthResponse?>,
                response: Response<AuthResponse?>
            ) {
                Log.d("LOGIN/SUCCESS",response.toString())
                val resp: AuthResponse? = if (response.isSuccessful) {
                    response.body()
                } else {
                    Gson().fromJson(response.errorBody()?.string(), AuthResponse::class.java)
                }

                when(val code = resp?.code?.trim()) {
                    "COMMON200_1" -> loginView.onLoginSuccess(code, resp.data!!)
                    else -> loginView.onLoginFailure()
                }
            }

            override fun onFailure(
                call: Call<AuthResponse?>,
                t: Throwable
            ) {
                Log.d("LOGIN/FAILURE",t.message.toString())
            }


        })
        Log.d("LOGIN","HELLO")

    }

//    fun test(token: String) {
//        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
//        val response = authService.test(token)
//
//        if(response.isSuccessful) {
//            val body = response.body()
//            if(body == null){
//                Log.d("tag", "Response body is null")
//                Result.failure(RuntimeException("Response body is null"))
//            }
//            else if(body.data == null){
//                Log.d("tag", "Response OK but Data is null")
//                Result.failure(RuntimeException("Response OK but Data is null"))
//            }
//            else{
//                Log.d("tag", "OK")
//                Result.success(body.data)
//            }
//        }
//        else{
//            val errMsg = response.errorBody()?.string() ?: response.message()
//            Result.failure(RuntimeException("HTTP ${response.code()}: $errMsg"))
//        }
//        Log.d("LOGIN","HELLO")
//
//    }
}