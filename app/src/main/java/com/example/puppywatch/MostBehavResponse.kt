package com.example.puppywatch

import com.google.gson.annotations.SerializedName

data class MostBehavResponse(
    @SerializedName("data") val data: List<ListData>,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String
)

data class ListData (
    @SerializedName("Date") val Date: String,
    @SerializedName("mostBehav") val mostBehav: String

)