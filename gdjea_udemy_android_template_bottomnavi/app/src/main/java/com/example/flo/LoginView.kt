package com.example.flo

import com.example.flo.data.remote.Result

interface LoginView {
    fun onLoginSuccess(code: Int, result: Result)
    fun onLoginFailure()
}