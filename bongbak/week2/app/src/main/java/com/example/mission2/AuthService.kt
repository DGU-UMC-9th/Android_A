package com.example.mission2

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView
    private lateinit var testView: TestView // 1. 뷰 변수 추가

    fun setTestView(testView: TestView) { // 2. 뷰 연결 함수
        this.testView = testView
    }

    fun setSignUpView(signUpView: SignUpView) {
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }
    fun testApi(jwt: String) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        val bearerToken = if (jwt.startsWith("Bearer")) jwt else "Bearer $jwt"

        authService.jwtTest(bearerToken).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val resp = response.body()!!
                    Log.d("TEST_API", "성공: ${resp.message}")

                    if (::testView.isInitialized) {
                        testView.onTestSuccess(resp.message ?: "인증 성공")
                    }
                } else {
                    Log.e("TEST_API", "실패: ${response.code()}")
                    if (::testView.isInitialized) {
                        testView.onTestFailure("인증 실패: ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.e("TEST_API", "네트워크 오류: ${t.message}")
                if (::testView.isInitialized) {
                    testView.onTestFailure("네트워크 오류")
                }
            }
        })
    }

    // 회원가입 실행
    fun signUp(user: User) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        // User 객체가 올바른지 확인 (필요 시)
        val request = User(user.email, user.password, user.name)

        authService.signUp(request).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val resp = response.body()!!
                    Log.d("SIGNUP/SUCCESS", "응답 코드: ${resp.code}")

                    // View가 초기화되었는지 확인 후 호출
                    if (::signUpView.isInitialized) {
                        if (resp.code == "COMMON201") {
                            signUpView.onSignUpSuccess()
                        } else {
                            // 서버 로직상 실패 (예: 중복 이메일 등 메시지 확인 필요)
                            Log.e("SIGNUP/FAIL", "실패 메시지: ${resp.message}")
                            signUpView.onSignUpFailure()
                        }
                    }
                } else {
                    // HTTP 상태 코드가 200번대가 아닐 때 (예: 400 Bad Request)
                    Log.e("SIGNUP/HTTP_ERR", "코드: ${response.code()}, 메시지: ${response.errorBody()?.string()}")
                    if (::signUpView.isInitialized) {
                        signUpView.onSignUpFailure()
                    }
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                // 네트워크 연결 끊김 또는 타임아웃
                Log.e("SIGNUP/NET_ERR", t.message.toString())
                if (::signUpView.isInitialized) {
                    signUpView.onSignUpFailure()
                }
            }
        })
    }

    // 로그인 실행
    fun login(user: User) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        val loginRequest = LoginRequest(user.email, user.password)

        authService.login(loginRequest).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("LOGIN/RESPONSE", response.toString())

                if (response.isSuccessful && response.body() != null) {
                    val resp: AuthResponse = response.body()!!
                    Log.d("LOGIN/SUCCESS", "응답 코드: ${resp.code}")

                    if (::loginView.isInitialized) {
                        if (resp.code == "COMMON200_1") {
                            loginView.onLoginSuccess(resp.code, resp.result!!)
                        } else {
                            Log.e("LOGIN/FAIL", "실패 메시지: ${resp.message}")
                            loginView.onLoginFailure()
                        }
                    }
                } else {
                    Log.e("LOGIN/HTTP_ERR", "코드: ${response.code()}, 메시지: ${response.errorBody()?.string()}")
                    if (::loginView.isInitialized) {
                        loginView.onLoginFailure()
                    }
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.e("LOGIN/NET_ERR", t.message.toString())
                if (::loginView.isInitialized) {
                    loginView.onLoginFailure()
                }
            }
        })
    }
}