package com.example.flo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySignUpBinding

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
        val id = binding.signUpIdEt.text.toString()
        val password = binding.signUpPasswordEt.text.toString()
        val name = binding.signUpNameEt.text.toString()



        val userDB = SongDatabase.getInstance(this)!!
        val user = User(email = id, password = password, name = name)

        userDB.userDao().insert(user)

        Toast.makeText(this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }
}