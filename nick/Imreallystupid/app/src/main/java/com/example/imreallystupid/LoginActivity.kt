package com.example.imreallystupid

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imreallystupid.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginActivity: AppCompatActivity(), LoginView {
    lateinit var binding: ActivityLoginBinding
    private lateinit var loginView: LoginView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginSignInBtn.setOnClickListener {
            login()
        }

        binding.loginKakakoLoginIv.setOnClickListener {
            loginWithKaKao(this)
        }
    }

    private fun loginWithKaKao(context : Context) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
                loginView.onLoginFailure()

            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")

                UserApiClient.instance.me { user, error ->
                    if (user != null) {
                        val kakaoId = user.id
                        val nickname = user.kakaoAccount?.profile?.nickname
                        val email = user.kakaoAccount?.email

                        val internalId =  (kakaoId!!.rem(Int.MAX_VALUE)).toInt()

                        saveJwt(internalId)
                        startActivity()
                        Log.d("LOGIN INFO",user.toString())
                        Log.d("LOGIN INFO",email.toString())
                        Log.d("LOGIN INFO",nickname.toString())
                        Log.d("LOGIN INFO",kakaoId.toString())
                    }
                }
            }
        }

// 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)


                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    private fun login() {
        if (binding.loginIdEt.text.toString().isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.loginPasswordEt.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val email : String = binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
        val pwd : String = binding.loginPasswordEt.text.toString()

//        val songDB = SongDatabase.getInstance(this)!!
//        val user = songDB.userDao().getUser(email, pwd)
//
//        user?.let {
//            Log.d("LOGIN_ACT/GET_USER","userId : ${user.id}, $user")
//            saveJwt(user.id)
//            startActivity()
//        } ?: {
//            Toast.makeText(this,"회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
//        }
        val authService = AuthService()
        authService.setLoginView(this)

        authService.login(User(email,pwd,""))
    }

//    private fun saveJwt(jwt:Int) {
//        val spf = getSharedPreferences("auth",MODE_PRIVATE)
//        val editor = spf.edit()
//
//        editor.putInt("jwt",jwt)
//        editor.apply()
//    }

    private fun saveJwt(jwt:Int) {
        val spf = getSharedPreferences("auth",MODE_PRIVATE)
        val editor = spf.edit()

        editor.putInt("jwt",jwt)
        editor.apply()
    }


    private fun startActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    override fun onLoginSuccess(code: String, data: Data) {
        when(code) {
            "COMMON200_1" -> {
                saveJwt(data.memberId)
                startActivity()
            }
        }
        Log.d("ACCESS_TOKEN", data.toString())
    }

    override fun onLoginFailure() {
        Toast.makeText(this,"회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
    }

}