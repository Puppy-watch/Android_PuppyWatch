package com.example.puppywatch.view

interface LoginView {
    fun onLoginSuccess(dogIdx: Int)
    fun onLoginFailure()
}