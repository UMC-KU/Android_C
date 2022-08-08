package com.example.flo.data.remote

import android.util.Log
import com.example.flo.SignUpView
import com.example.flo.data.entities.User
import com.example.flo.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AuthService {
    private lateinit var signUpView: SignUpView
    fun setSignUpView(signUpView: SignUpView) {
        this.signUpView = signUpView
    }

    fun signUp(user: User) {

        val signUpService = getRetrofit().create(AuthRetrofitInterface::class.java)

        signUpService.signUp(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful && response.code() == 200) {
                    val signUpResponse: AuthResponse = response.body()!!

                    Log.d("SIGNUP-RESPONSE", signUpResponse.toString())

                    when (val code = signUpResponse.code) {
                        1000 -> signUpView.onSignUpSuccess()
                        else -> signUpView.onSignUpFailure()
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<AuthResponse>, t: Throwable) {
                Log.d("SIGHUP/FAILRE",t.message.toString())
            }
        })
        Log.d("SIGHUP","HELLO")


    }

}