package com.example.imreallystupid

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imreallystupid.databinding.ActiviySignupBinding
import com.google.gson.Gson
import okhttp3.Callback
import retrofit2.Call
import retrofit2.Response
import retrofit2.create
import kotlin.math.log

class SignUpActivity : AppCompatActivity(), SignUpView {
    lateinit var binding: ActiviySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActiviySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpSignUpBtn.setOnClickListener {
            signUp()
        }
    }
    


    private fun getUser(): User {
        val email : String = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd : String = binding.signUpPasswordEt.text.toString()
        var name: String = binding.signUpNameEt.text.toString()
        return User(email, pwd, name)
    }

    //RoomDB 회원 가입
//    private fun signUp() {
//        if (binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()) {
//            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
//            return
//        }
//        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
//            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val userDB = SongDatabase.getInstance(this)!!
//        userDB.userDao().insert(getUser())
//
//        val user = userDB.userDao().getUsers()
//        Log.d("SIGNUPACT",user.toString())
//    }

    private fun signUp() {
        if (binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signUpNameEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이름 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val authService = AuthService()
        authService.setSignUpView(this)

        authService.signUp(getUser())
    }

    override fun onSignUpSuccess() {
        finish()
    }

    override fun onSignUpFailure() {
        binding.signUpEmailErrorTv.visibility = View.VISIBLE
        binding.signUpEmailErrorTv.text = "error"
    }
}