package com.example.puppywatch.view

import com.example.puppywatch.response.ListData

interface MostBehaviorView {
    fun onMostBehaviorSuccess(data: List<ListData>)
    fun onMostBehaviorFailure()
}