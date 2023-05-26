package com.example.puppywatch.response

import com.google.gson.annotations.SerializedName

data class JoinResponse (
    @SerializedName("code") val code: Int,
    @SerializedName("dogIdx") val dogIdx: Int,
    @SerializedName("message") val message: String,
    @SerializedName("userIdx") val userIdx: Int
)