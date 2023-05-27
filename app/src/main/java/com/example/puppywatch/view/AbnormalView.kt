package com.example.puppywatch.view

import com.example.puppywatch.response.AbnormalResponse


interface AbnormalView {
    fun onAbnormalSuccess(abnormalList: AbnormalResponse)
    fun onAbnormalFailure()
}