package com.example.puppywatch

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthRetrofitInterface {
    // 회원가입
    @POST("/signup")
    fun join(
        @Body user: UserSign
    ): Call<JoinResponse>

    // 로그인
    @POST("/login")
    fun login(
        @Body user: User
    ): Call<LoginResponse>

    // 현재 행동 라벨
    @GET("/behavior")
    fun nowBehavior(
        @Query("dog_idx") dog_idx: Int
    ) : Call<NowBehaviorResponse>
}