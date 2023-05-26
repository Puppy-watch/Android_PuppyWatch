package com.example.puppywatch

interface MostBehaviorView {
    fun onMostBehaviorSuccess(data: List<ListData>)
    fun onMostBehaviorFailure()
}