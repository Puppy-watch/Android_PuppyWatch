package com.example.puppywatch.view

import com.example.puppywatch.response.StatisticResponse

interface StatisticView {

    fun onStatisticSuccess(data: StatisticResponse)
    fun onStatisticFailure()
}