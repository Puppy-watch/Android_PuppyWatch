package com.example.puppywatch.response

import com.google.gson.annotations.SerializedName

data class MypageResponse(
        @SerializedName("code") val code: Int,
        @SerializedName("message") val message: String
)