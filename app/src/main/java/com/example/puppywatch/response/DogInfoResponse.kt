package com.example.puppywatch.response

import com.google.gson.annotations.SerializedName

data class DogInfoResponse (
    @SerializedName("code") val code: Int,
    @SerializedName("dogAge") val dogAge: Int,
    @SerializedName("dogName") val dogName: String,
    @SerializedName("dogWeight") val dogWeight: Double,
    @SerializedName("firstTime") val firstTime: String,
    @SerializedName("message") val message: String,
    @SerializedName("secondTime") val secondTime: String,
    @SerializedName("thirdTime") val thirdTime: String
)