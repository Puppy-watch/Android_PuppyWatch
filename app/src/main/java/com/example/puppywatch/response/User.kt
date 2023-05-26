package com.example.puppywatch.response

import com.google.gson.annotations.SerializedName

data class UserSign(
    @SerializedName("userId") val userId: String,
    @SerializedName("userPw") val userPw: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("dogName") val dogName: String
)

data class User(
    @SerializedName("userId") val userId: String,
    @SerializedName("userPw") val userPw: String,
)