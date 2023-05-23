package com.example.puppywatch

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private final var TAG = "AuthService"

    private lateinit var joinView: JoinView
    private lateinit var loginView: LoginView

    fun setJoinView(joinView: JoinView) {
        this.joinView = joinView
    }

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }


    fun join(user: UserSign) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.join(user).enqueue(object: Callback<JoinResponse> {
            override fun onResponse(call: Call<JoinResponse>, response: Response<JoinResponse>) {
                if(response.body() != null) {
                    Log.d("JOIN/SUCCESS", response.toString())

                    val resp: JoinResponse = response.body()!!
                    when(resp.code) {
                        200 -> joinView.onJoinSuccess()
                        else -> joinView.onJoinFailure()
                    }
                }
            }

            override fun onFailure(call: Call<JoinResponse>, t: Throwable) {
                Log.d("JOIN/FAILURE", t.message.toString())
            }
        })

        Log.d("JOIN()/", "메소드")
    }

    fun login(user: User) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.login(user).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.body() != null) {
                    Log.d(TAG, "LOGIN/SUCCESS $response")

                    val resp: LoginResponse = response.body()!!
                    when (val code = resp.code) {
                        200 -> loginView.onLoginSuccess()
                        else -> loginView.onLoginFailure()
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d(TAG, "LOGIN/FAILURE" + t.message.toString())
            }
        })

        Log.d("LOGIN()/", "메소드")
    }
}