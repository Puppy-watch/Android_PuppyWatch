package com.example.puppywatch.response

import com.google.gson.annotations.SerializedName

data class MostBehaviorResponse (
    @SerializedName("code") val code: Int,
    @SerializedName("data") val data: ListData,
    @SerializedName("message") val message: String
)
data class ListData (
    @SerializedName("Date") val Date: String,
    @SerializedName("mostBehav") val mostBehav: String
)