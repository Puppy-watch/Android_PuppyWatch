package com.example.puppywatch

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
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
import com.github.mikephil.charting.formatter.ValueFormatter



class CustomDialog(
    private val context: Context,
    private val authService: AuthService,
    private val click_date: String
) : StatisticView {

    private lateinit var onClickListener: OnDialogClickListener
    private val dialog = Dialog(context)
    private lateinit var staticContent: StatisticResponse
    private lateinit var chart: PieChart

    // dog_idx 가져오기
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val dog_idx = sharedPreferences.getInt("dog_idx", 0)

    fun setOnClickListener(listener: OnDialogClickListener) {
        onClickListener = listener
    }

    fun detailInitViews() {
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

        // 차트 초기화
        chart = dialog.findViewById(R.id.chart)

        statistic()
    }

    private fun statistic() {
        Log.d("statistic()", "메소드")
        Log.d("CustomDialog / dog_idx", dog_idx.toString())
        authService.setStatisticView(this)
        authService.statistic(click_date, dog_idx)
    }
    private fun formatValueWithHours(value: Float): String {
        return "${value.toInt()} 분"
    }

    override fun onStatisticSuccess(data: StatisticResponse) {
        Log.d("Statistic", data.toString())
        staticContent = data

        // 서버로부터 통계 데이터를 가져온 후에 차트 생성
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

        //값이 0인 데이터 걸러내기
        val filteredEntries = entries.filter { it.value != 0f }

        val dataSet = PieDataSet(filteredEntries, "Chart")
        for (c in ColorTemplate.PASTEL_COLORS) dataSet.colors.add(c)

        dataSet.setDrawValues(true)

        //value 색과 크기 변경
        dataSet.setValueTextColor(Color.WHITE)
        dataSet.setValueTextSize(15f)

        val data = PieData(dataSet)

        // 차트에 데이터 설정
        chart.data = data

        val valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return formatValueWithHours(value)
            }
        }
        dataSet.valueFormatter = valueFormatter
        chart.invalidate()


        // Dialog 표시
        dialog.show()
    }

    override fun onStatisticFailure() {
        Log.d("onStatisticFailure", "실패")
    }

    interface OnDialogClickListener {
        fun onClicked(flag: Boolean)
    }
}