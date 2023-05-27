package com.example.puppywatch

import android.util.Log
import com.example.puppywatch.response.*
import com.example.puppywatch.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private final var TAG = "AuthService"

    private lateinit var joinView: JoinView
    private lateinit var loginView: LoginView
    private lateinit var nowBehaviorView: NowBehaviorView
    private lateinit var mostBehaviorView: MostBehaviorView
    private lateinit var statisticView: StatisticView


    fun setJoinView(joinView: JoinView) {
        this.joinView = joinView
    }

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun setNowBehaviorView(nowBehaviorView: NowBehaviorView) {
        this.nowBehaviorView = nowBehaviorView
    }

    fun setMostBehaviorView(mostBehaviorView: MostBehaviorView) {
        this.mostBehaviorView = mostBehaviorView
    }

    fun setStatisticView(statisticView: StatisticView){
        this.statisticView = statisticView
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

    fun nowBehavior(dog_idx: Int) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.nowBehavior(dog_idx).enqueue(object : Callback<NowBehaviorResponse> {
            override fun onResponse(call: Call<NowBehaviorResponse>, response: Response<NowBehaviorResponse>) {
                if(response.body() != null) {
                    Log.d(TAG, "NowBehavior/SUCCESS $response")

                    val resp: NowBehaviorResponse = response.body()!!
                    when (resp.code) {
                        200 -> nowBehaviorView.onNowBehaviorSuccess(resp.nowBehav!!)
                        else -> nowBehaviorView.onNowBehaviorFailure()
                    }
                } else {
                    nowBehaviorView.onNowBehaviorFailure()
                }
            }

            override fun onFailure(call: Call<NowBehaviorResponse>, t: Throwable) {
                Log.d(TAG, "NowBehavior/FAILURE" + t.message.toString())
            }
        })

        Log.d("NowBehavior()/", "메소드")
    }

    fun mostBehavior(dog_idx: Int) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.mostBehavior(dog_idx).enqueue(object : Callback<MostBehaviorResponse> {
            override fun onResponse(call: Call<MostBehaviorResponse>, response: Response<MostBehaviorResponse>) {
                if(response.body() != null) {
                    Log.d(TAG, "MostBehavior/SUCCESS $response")
                    val resp: MostBehaviorResponse = response.body()!!

                    when (resp.code) {
                        200 -> {mostBehaviorView.onMostBehaviorSuccess(resp.data)}
                        else -> mostBehaviorView.onMostBehaviorFailure()
                    }
                } else {
                    mostBehaviorView.onMostBehaviorFailure()
                }
            }

            override fun onFailure(call: Call<MostBehaviorResponse>, t: Throwable) {
                Log.d(TAG, "MostBehavior/FAILURE" + t.message.toString())
            }
        })

        Log.d("MostBehavior()/", "메소드")
    }


    fun statistic(date:String,dog_idx: Int) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.statistic(date,dog_idx).enqueue(object : Callback<StatisticResponse> {
            override fun onResponse(call: Call<StatisticResponse>, response: Response<StatisticResponse>) {
                if(response.body() != null) {
                    //Log.d(TAG, "Statistic/SUCCESS $response")
                    val resp: StatisticResponse = response.body()!!
                    when (resp.code) {
                        200 -> statisticView.onStatisticSuccess(resp)
                        else -> statisticView.onStatisticFailure()
                    }
                } else {
                    statisticView.onStatisticFailure()
                }
            }

            override fun onFailure(call: Call<StatisticResponse>, t: Throwable) {
                Log.d(TAG, "Statistic/FAILURE" + t.message.toString())
            }
        })

        Log.d("Statistic()/", "메소드")
    }



}