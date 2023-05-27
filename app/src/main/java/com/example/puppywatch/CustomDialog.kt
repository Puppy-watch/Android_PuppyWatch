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
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

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

            //차트 만들기
            val chart = dialog.findViewById<PieChart>(R.id.chart)

            statistic()

            val entries = listOf(
                PieEntry(staticContent.bite.toFloat(), "bite"),
                PieEntry(staticContent.eat.toFloat(), "eat"),
                PieEntry(staticContent.seat.toFloat(), "sit"),
                PieEntry(staticContent.sleep.toFloat(), "sleep"),
                PieEntry(staticContent.walk.toFloat(), "walk"),
                PieEntry(staticContent.slowWalk.toFloat(), "slowWalk"),
                PieEntry(staticContent.stand.toFloat(), "stand"),
                PieEntry(staticContent.run.toFloat(), "run")

            )
            val dataSet = PieDataSet(entries, "Chart")
            //dataSet.colors = listOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)
            for (c in ColorTemplate.PASTEL_COLORS) dataSet.colors.add(c)
            val data = PieData(dataSet)
            chart.data = data
            chart.invalidate()

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