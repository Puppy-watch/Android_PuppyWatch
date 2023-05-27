package com.example.puppywatch

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import com.example.puppywatch.response.StatisticResponse
import com.example.puppywatch.view.StatisticView

class CustomDialog (
    context: Context, private val authService: AuthService,
    private val click_date: String) : StatisticView { // 뷰를 띄워야하므로 Dialog 클래스는 context를 인자로 받는다.

        private lateinit var onClickListener: OnDialogClickListener
        private val dialog = Dialog(context)
        lateinit var staticContent : StatisticResponse


        fun setOnClickListener(listener: OnDialogClickListener)
        {
            onClickListener = listener
        }

        fun detailInitViews(){

            statistic()

            // Dialog 컨텐츠 뷰 설정
            dialog.setContentView(R.layout.dialog_detail)

            // Dialog 뷰 초기화
            val dialogNoImageView = dialog.findViewById<ImageView>(R.id.dialog_no_iv)
            dialogNoImageView.setOnClickListener {
                dialog.dismiss()
            }

            // Dialog 창 설정
            dialog.window?.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)

            // Dialog 표시
            dialog.show()

        }

        interface OnDialogClickListener
        {
            fun onClicked(flag: Boolean)
        }
    private fun statistic() {
        Log.d("statistic()", "메소드")
        val authService = AuthService()
        authService.setStatisticView(this)
        authService.statistic(click_date,1)
    }

    override fun onStatisticFailure() {
        //Log.d("Statistic", "실패")
    }

    override fun onStatisticSuccess(data: StatisticResponse) {
        Log.d("Statistic", data.toString())
        staticContent = data

    }

    }