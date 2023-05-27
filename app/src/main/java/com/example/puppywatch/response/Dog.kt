package com.example.puppywatch.response

import com.google.gson.annotations.SerializedName

data class Dog(
        @SerializedName("dogName") val dogName: String,
        @SerializedName("dogAge") val dogAge: Int,
        @SerializedName("dogWeight")val dogWeight: Double,
        @SerializedName("firstTime") val firstTime: String,
        @SerializedName("secondTime") val secondTime: String,
        @SerializedName("thirdTime") val thirdTime: String
)