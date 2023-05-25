package com.example.puppywatch

import com.google.gson.annotations.SerializedName

data class NowBehaviorResponse (
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("nowBehav") val nowBehav: String
)