package com.example.puppywatch

import com.example.puppywatch.response.*
import retrofit2.Call
import retrofit2.http.*

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

    // 아이디 중복 체크
    @POST("/checkId")
    fun checkId(
        @Body user: UserIdCheck
    ): Call<CheckIdResponse>

    // 현재 행동 라벨
    @GET("/behavior")
    fun nowBehavior(
        @Query("dog_idx") dog_idx: Int
    ) : Call<NowBehaviorResponse>

    //가장 많이 한 행동
    @GET("/mostBehav")
    fun mostBehavior(
        @Query("dog_idx") dog_idx: Int
    ) : Call<MostBehaviorResponse>

    // 이상 행동
    @GET("/abnormals")
    fun abnormals(
        @Query("dog_idx") dog_idx: Int
    ) : Call<AbnormalResponse>

    // 행동 통계
   @GET("/statistic")
    fun statistic(
        @Query("date") date: String,
        @Query("dog_idx") dog_idx: Int
    ) : Call<StatisticResponse>

    //마이페이지
    @PUT("/dogs/{dog_idx}")
    fun mypage(
        @Path("dog_idx") dog_Idx: Int,
        @Body dog: Dog
    ): Call<MypageResponse>
}