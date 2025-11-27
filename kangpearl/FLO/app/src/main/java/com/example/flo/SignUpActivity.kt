package com.example.flo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySignUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpBtn.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        val email = binding.signUpIdEt.text.toString()
        val password = binding.signUpPasswordEt.text.toString()
        val name = binding.signUpNameEt.text.toString()

        if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "이메일, 비밀번호, 닉네임을 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.signUp(User(email, password, name)).enqueue(object : Callback<AuthResponse<SignUpResult>> {
            override fun onResponse(call: Call<AuthResponse<SignUpResult>>, response: Response<AuthResponse<SignUpResult>>) {
                Log.d("SIGNUP/RESPONSE", response.toString())

                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    runOnUiThread {
                        Toast.makeText(this@SignUpActivity, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    val msg = response.body()?.message ?: "회원가입 실패"
                    runOnUiThread {
                        Toast.makeText(this@SignUpActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<AuthResponse<SignUpResult>>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }
        })
    }
}