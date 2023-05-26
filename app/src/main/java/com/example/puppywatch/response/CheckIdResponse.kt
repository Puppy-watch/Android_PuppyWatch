package com.example.puppywatch.response

import com.google.gson.annotations.SerializedName

data class CheckIdResponse (
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String
)