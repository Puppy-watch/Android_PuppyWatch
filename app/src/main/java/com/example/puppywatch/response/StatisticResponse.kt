package com.example.puppywatch.response

import com.google.gson.annotations.SerializedName

data class StatisticResponse(
    @SerializedName("Date") val Date: String,
    @SerializedName("bite") val bite: Int,
    @SerializedName("code") val code: Int,
    @SerializedName("eat") val eat: Int,
    @SerializedName("message") val message: String,
    @SerializedName("run") val run: Int,
    @SerializedName("seat") val seat: Int,
    @SerializedName("sleep") val sleep: Int,
    @SerializedName("slowWalk") val slowWalk: Int,
    @SerializedName("stand") val stand: Int,
    @SerializedName("walk") val walk: Int
)