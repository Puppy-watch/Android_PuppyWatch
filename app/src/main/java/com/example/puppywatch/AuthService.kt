package com.example.puppywatch

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private final var TAG = "AuthService"

    private lateinit var joinView: JoinView
    private lateinit var loginView: LoginView
    private lateinit var mostBehavView: MostBehavView
    private lateinit var nowBehavView: NowBehavView

    fun setJoinView(joinView: JoinView) {
        this.joinView = joinView
    }

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun setMostBehavView(mostBehavView: MostBehavView) {
        this.mostBehavView = mostBehavView
    }

    fun setNowBehavView(nowBehavView: NowBehavView) {
        this.nowBehavView = nowBehavView
    }

    fun join(user: UserSign) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.join(user).enqueue(object: Callback<JoinResponse> {
            override fun onResponse(call: Call<JoinResponse>, response: Response<JoinResponse>) {
                if(response.body() != null) {
                    Log.d(TAG, "JOIN/SUCCESS $response")

                    val resp: JoinResponse = response.body()!!
                    when (resp.code) {
                        200 -> joinView.onJoinSuccess()
                        else -> joinView.onJoinFailure()
                    }
                } else {
                    joinView.onJoinFailure()
                }
            }

            override fun onFailure(call: Call<JoinResponse>, t: Throwable) {
                Log.d(TAG, "JOIN/FAILURE" + t.message.toString())
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
                    when (resp.code) {
                        200 -> loginView.onLoginSuccess()
                        else -> loginView.onLoginFailure()
                    }
                } else {
                    loginView.onLoginFailure()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d(TAG, "LOGIN/FAILURE" + t.message.toString())
            }
        })

        Log.d("LOGIN()/", "메소드")
    }

    fun nowBehav() {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.nowBehav().enqueue(object: Callback<NowBehavResponse> {
            override fun onResponse(call: Call<NowBehavResponse>, response: Response<NowBehavResponse>) {
                if(response.body() != null) {
                    Log.d("NowBehavior/SUCCESS", response.toString())

                    val resp: NowBehavResponse = response.body()!!

                    nowBehavView.changeIcon("sit")
                    Log.d("now behavior data",resp.nowBehav)
                    when(resp.code) {
                        200 -> nowBehavView.NowBehavSuccess()
                        else -> nowBehavView.NowBehavFailure()
                    }
                }
            }

            override fun onFailure(call: Call<NowBehavResponse>, t: Throwable) {
                Log.d("nowBehav/FAILURE", t.message.toString())
            }
        })

        Log.d("NowBehav()/", "메소드")
    }


    fun mostBehav() {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.mostBehav().enqueue(object: Callback<MostBehavResponse> {
            override fun onResponse(call: Call<MostBehavResponse>, response: Response<MostBehavResponse>) {
                if(response.body() != null) {
                    Log.d("mostBehav/SUCCESS", response.toString())
                    val resp: MostBehavResponse = response.body()!!
                    Log.d("Data",resp.data.toString())

                    when(resp.code) {
                        200 -> mostBehavView.MostBehavSuccess()
                        else -> mostBehavView.MostBehavFailure()
                    }
                }
            }

            override fun onFailure(call: Call<MostBehavResponse>, t: Throwable) {
                Log.d("MostBehavior/FAILURE", t.message.toString())
            }
        })

        Log.d("MostBehavior()/", "메소드")
    }
}