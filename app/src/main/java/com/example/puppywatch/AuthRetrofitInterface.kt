package com.example.puppywatch

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/signup")
    fun join(@Body user: UserSign): Call<JoinResponse>
}