package com.example.mission2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mission2.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity: AppCompatActivity(), SignUpView {
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpSignUpBtn.setOnClickListener {
            signUp()
        }
    }

    private fun getUser(): User {
        val email: String =
            binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd: String = binding.signUpPasswordEt.text.toString()
        val name: String = binding.signUpNameEt.text.toString()

        return User(email, pwd, name)
    }

    private fun signUp() {
        // 1. 입력값 가져오기
        val inputId = binding.signUpIdEt.text.toString()
        val inputDomain = binding.signUpDirectInputEt.text.toString()

        // 2. 이메일 합체!
        val realEmail = "$inputId@$inputDomain"

        // ★★★ 여기가 핵심! 로그캣에 "SIGNUP_CHECK"로 검색해서 이 줄을 보세요! ★★★
        Log.d("SIGNUP_CHECK", "서버로 보내는 진짜 이메일: $realEmail")
        if (binding.signUpIdEt.text.toString()
                .isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.signUpNameEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이름 형식이 잘못되었습니다", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            return
        }

        val authService = AuthService()
        authService.setSignUpView(this)

        authService.signUp(getUser())
    }

    override fun onSignUpSuccess(){
        finish()
    }
    override fun onSignUpFailure() {

    }
}

//    private fun signUp(){
//        if(binding.signUpIdEt.text.toString().isEmpty()||binding.signUpDirectInputEt.text.toString().isEmpty()){
//            Toast.makeText(this,"이메일 형식이 잘못되었습니다",Toast.LENGTH_SHORT).show()
//            return
//        }
//        if(binding.signUpPasswordEt.text.toString()!=binding.signUpPasswordEt.text.toString()){
//            Toast.makeText(this,"비밀번호가 일치하지 않습니다",Toast.LENGTH_SHORT).show()
//            return
//        }
//        val userDB=SongDatabase.getInstance(this)!!
//        userDB.UserDao().insert(getUser())
//
//        val users = userDB.UserDao().getUsers()
//
//        Log.d("SIGNUPACT", users.toString())
//    }