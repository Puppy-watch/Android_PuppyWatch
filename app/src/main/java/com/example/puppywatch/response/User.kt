package com.example.puppywatch.response

import com.google.gson.annotations.SerializedName

// 회원가입
data class UserSign(
    @SerializedName("userId") val userId: String,
    @SerializedName("userPw") val userPw: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("dogName") val dogName: String
)

// 로그인
data class User(
    @SerializedName("userId") val userId: String,
    @SerializedName("userPw") val userPw: String,
)

// 아이디 중복 체크
data class UserIdCheck(
    @SerializedName("userId") val userId: String
)