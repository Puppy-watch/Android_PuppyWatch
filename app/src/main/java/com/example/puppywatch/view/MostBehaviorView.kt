package com.example.puppywatch.view

import com.example.puppywatch.response.ListData

interface MostBehaviorView {
    fun onMostBehaviorSuccess(data: ListData)
    fun onMostBehaviorFailure()
}