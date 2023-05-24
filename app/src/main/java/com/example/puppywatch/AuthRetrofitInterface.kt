package com.example.puppywatch

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
//    @Headers("Key: Cookie", "Value: Session")
    fun login(
//        @HeaderMap headers: Map<String, String>,
        @Body user: User
    ): Call<LoginResponse>

    // 이상행동
    @GET("/results/{postDetailId}")
    fun behavior(
        @Body user: User
    ): Call<LoginResponse>

    @GET("/behavior")
    fun nowBehav() : Call<NowBehavResponse>

    @GET("/mostBehav")
    fun mostBehav() : Call<MostBehavResponse>
}