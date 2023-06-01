package com.example.puppywatch.view

import com.example.puppywatch.response.DogInfoResponse

interface DogInfoView {
    fun onDogInfoSuccess(dogInfoList: DogInfoResponse)
    fun onDogInfoFailure()
}