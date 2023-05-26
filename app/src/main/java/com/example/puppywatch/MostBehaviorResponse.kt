package com.example.puppywatch

import com.google.gson.annotations.SerializedName

data class MostBehaviorResponse (
    @SerializedName("code") val code: Int,
    @SerializedName("data") val data: List<ListData>,
    @SerializedName("message") val message: String
)

data class ListData (
    @SerializedName("Date") val Date: String,
    @SerializedName("mostBehav") val mostBehav: String
)