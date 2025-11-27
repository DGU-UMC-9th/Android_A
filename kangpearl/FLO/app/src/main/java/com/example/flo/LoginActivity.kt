package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginBtn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.loginIdEt.text.toString()
        val password = binding.loginPwdEt.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "정보를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.login(User(email, password, "")).enqueue(object : Callback<AuthResponse<LoginResult>> {
            override fun onResponse(call: Call<AuthResponse<LoginResult>>, response: Response<AuthResponse<LoginResult>>) {
                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    val result = response.body()?.result!!

                    val spf = getSharedPreferences("auth", MODE_PRIVATE)
                    val editor = spf.edit()
                    editor.putString("jwt", result.accessToken)
                    editor.putInt("userId", result.memberId)
                    editor.putString("name", result.name)
                    editor.commit()

                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "로그인 성공!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                } else {
                    val msg = response.body()?.message ?: "로그인 실패"
                    Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AuthResponse<LoginResult>>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
            }
        })
    }
}