package com.example.puppywatch.response

import com.google.gson.annotations.SerializedName

data class AbnormalResponse (
    @SerializedName("code") val code: Int,
    @SerializedName("data") val data: List<AbListData>,
    @SerializedName("message") val message: String
)

data class AbListData (
    @SerializedName("abnormalName") val abnormalName: String,
    @SerializedName("abnormalTime") val abnormalTime: String
)