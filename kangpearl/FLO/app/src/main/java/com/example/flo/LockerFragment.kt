package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding
    private val information = arrayListOf("저장한 곡", "저장앨범")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        val lockerAdapter = LockerVPAdapter(this)
        binding.lockerContentVp.adapter = lockerAdapter

        TabLayoutMediator(binding.lockerContentTb, binding.lockerContentVp) { tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initViews()
    }

    private fun initViews() {
        val jwt = getJwt()
        Log.d("LOCKER", "JWT Check: $jwt")

        if (jwt == "") {
            binding.lockerLoginLayout.visibility = View.VISIBLE
            binding.lockerContentVp.visibility = View.GONE
            binding.lockerContentTb.visibility = View.GONE

            binding.lockerLoginTv.text = "로그인"
            binding.lockerLoginTv.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        } else {
            binding.lockerLoginLayout.visibility = View.GONE
            binding.lockerContentVp.visibility = View.VISIBLE
            binding.lockerContentTb.visibility = View.VISIBLE

            binding.lockerLoginTv.text = "로그아웃"
            binding.lockerLoginTv.setOnClickListener {
                logout()
            }
        }
    }

    private fun getJwt(): String {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf?.getString("jwt", "") ?: ""
    }

    private fun logout() {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = spf?.edit()
        editor?.remove("jwt")
        editor?.remove("userId")
        editor?.apply()

        initViews()
    }
}