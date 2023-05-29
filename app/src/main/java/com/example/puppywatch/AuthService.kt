package com.example.puppywatch

import android.util.Log
import com.example.puppywatch.response.*
import com.example.puppywatch.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body

class AuthService {
    private final var TAG = "AuthService"

    private lateinit var joinView: JoinView
    private lateinit var loginView: LoginView
    private lateinit var checkIdView: CheckIdView
    private lateinit var nowBehaviorView: NowBehaviorView
    private lateinit var mostBehaviorView: MostBehaviorView
    private lateinit var abnormalView: AbnormalView
    private lateinit var statisticView: StatisticView
    private lateinit var mypageView: MypageView

    fun setJoinView(joinView: JoinView) {
        this.joinView = joinView
    }

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun setCheckIdView(checkIdView: CheckIdView) {
        this.checkIdView = checkIdView
    }

    fun setNowBehaviorView(nowBehaviorView: NowBehaviorView) {
        this.nowBehaviorView = nowBehaviorView
    }

    fun setMostBehaviorView(mostBehaviorView: MostBehaviorView) {
        this.mostBehaviorView = mostBehaviorView
    }

    fun setAbnormalView(abnormalView: AbnormalView) {
        this.abnormalView = abnormalView
    }

    fun setStatisticView(statisticView: StatisticView){
        this.statisticView = statisticView
    }
    fun setMypageView(mypageView: MypageView){
        this.mypageView = mypageView
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
                        200 -> loginView.onLoginSuccess(resp.dogIdx!!)
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

    fun checkID(user: UserIdCheck) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.checkId(user).enqueue(object : Callback<CheckIdResponse> {
            override fun onResponse(call: Call<CheckIdResponse>, response: Response<CheckIdResponse>) {
                if(response.body() != null) {
                    Log.d(TAG, "CheckId/SUCCESS $response")

                    val resp: CheckIdResponse = response.body()!!
                    when (resp.code) {
                        200 -> checkIdView.onCheckIdSuccess(resp.code)
                        else -> checkIdView.onCheckIdFailure()
                    }
                } else {
                    checkIdView.onCheckIdFailure()
                }
            }

            override fun onFailure(call: Call<CheckIdResponse>, t: Throwable) {
                Log.d(TAG, "CheckId/FAILURE" + t.message.toString())
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
                        200 -> mostBehaviorView.onMostBehaviorSuccess(resp.data)
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

    fun abnormal(dog_idx: Int) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.abnormals(dog_idx).enqueue(object : Callback<AbnormalResponse> {
            override fun onResponse(call: Call<AbnormalResponse>, response: Response<AbnormalResponse>) {
                if(response.body() != null) {
                    Log.d(TAG, "Abnormal/SUCCESS $response")

                    val resp: AbnormalResponse = response.body()!!
                    when (resp.code) {
                        200 -> abnormalView.onAbnormalSuccess(resp)
                        else -> abnormalView.onAbnormalFailure()
                    }
                } else {
                    abnormalView.onAbnormalFailure()
                }
            }

            override fun onFailure(call: Call<AbnormalResponse>, t: Throwable) {
                Log.d(TAG, "Abnormal/FAILURE" + t.message.toString())
            }
        })

        Log.d("Abnormal()/", "메소드")
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
    fun mypage(dog_Idx: Int, dog: Dog) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.mypage(dog_Idx,dog).enqueue(object : Callback<MypageResponse> {
            override fun onResponse(call: Call<MypageResponse>, response: Response<MypageResponse>) {
                if(response.body() != null) {
                    val resp: MypageResponse = response.body()!!
                    when (resp.code) {
                        200 -> mypageView.onMypageSuccess()
                        else -> mypageView.onMypageFailure()
                    }
                } else {
                    mypageView.onMypageFailure()
                }
            }

            override fun onFailure(call: Call<MypageResponse>, t: Throwable) {
                Log.d(TAG, "Mypage/FAILURE" + t.message.toString())
            }
        })

        Log.d("Mypage()/", "메소드")
    }

}