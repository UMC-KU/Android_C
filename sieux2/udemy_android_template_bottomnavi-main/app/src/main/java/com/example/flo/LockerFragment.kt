package com.example.flo

import android.app.AppComponentFactory
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding

    private val information = arrayListOf("저장된 음악","음악파일","저장앨범")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        val lockerVPAdapter = LockerVPAdapter(this)
        binding.lockerContentVp.adapter = lockerVPAdapter
        TabLayoutMediator(binding.lockerContentTb,binding.lockerContentVp){
            tab, position ->
            tab.text = information[position]
        }.attach()

        binding.lockerLoginTv.setOnClickListener{
            startActivity(Intent(activity, LoginActivity::class.java))

        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initViews()
    }

    private fun getJwt(): Int{
        val spf = activity?.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt",0)
    }

    private fun initViews() {
        val jwt: Int = getJwt()

        if (jwt == 0){
            binding.lockerLoginTv.text = "로그인"
            binding.lockerLoginTv.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
        else{
            binding.lockerLoginTv.text = "로그아웃"
            binding.lockerLoginTv.setOnClickListener {
                logout()
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
    }

    private fun logout() {
        val spf = activity?.getSharedPreferences("auth" , AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()

        editor.remove("jwt")
        editor.apply()
    }




}