package com.example.flo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.data.entities.User
import com.example.flo.data.remote.AuthService
import com.example.flo.databinding.ActivitySignupBinding

class SignupActivity: AppCompatActivity(), SignUpView {

    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpSignUpBtn.setOnClickListener{
            signUp()
//            finish()
        }
    }

    private fun getUser(): User {
        val email: String = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val password: String = binding.signUpPasswordEt.text.toString()
        var name: String = binding.signUpNameEt.text.toString()

        return User(email, password, name)
    }

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
//        Log.d("SIGNUP EVENT", user.toString())
//    }

    private fun signUp() {
        if (binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signUpNameEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이름 형식이 잘못었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

//        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
//        authService.signUp(getUser()).enqueue(object: Callback<AuthResponse>{
//            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
//                Log.d("Signup/SUCCESS", response.toString())
//                val resp: AuthResponse = response.body()!!
//                when(resp.code){
//                    1000 -> finish()
//                    2016, 2018 -> {
//                        binding.signUpEmailErrorTv.visibility = View.VISIBLE
//                        binding.signUpEmailErrorTv.text = resp.message
//                    }
//                }
//            }
//            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
//                Log.d("Signup/FAILURE", t.message.toString())
//            }
//        })
//        Log.d("SignUp", "HELLO")

        val authService = AuthService()
        authService.setSignUpView(this)

        authService.signUp(getUser())
    }

    override fun onSignUpSuccess() {
        finish()
    }

    override fun onSignUpFailure() {

    }
}