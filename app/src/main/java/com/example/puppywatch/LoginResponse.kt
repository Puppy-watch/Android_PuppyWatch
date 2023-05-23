package com.example.puppywatch

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("code") val code: Int,
    @SerializedName("dogIdx") val dogIdx: Int,
    @SerializedName("message") val message: String,
    @SerializedName("userIdx") val userIdx: Int
)