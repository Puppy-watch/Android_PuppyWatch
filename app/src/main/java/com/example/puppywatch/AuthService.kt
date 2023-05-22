package com.example.puppywatch

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private final var TAG = "AuthService"

    private lateinit var joinView: JoinView

    fun setJoinView(joinView: JoinView) {
        this.joinView = joinView
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
}