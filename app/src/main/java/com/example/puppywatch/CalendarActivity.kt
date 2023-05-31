package com.example.puppywatch

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.puppywatch.databinding.ActivityCalendarBinding
import com.example.puppywatch.response.ListData
import com.example.puppywatch.view.MostBehaviorView

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class CalendarActivity : ComponentActivity(),OnItemListener {

    lateinit var  selectedData: LocalDate
    private lateinit var binding: ActivityCalendarBinding
    private lateinit var sharedPreferences: SharedPreferences
    //val mostBehavList : MutableList<ListData> = mutableListOf()

    companion object {
        var dog_idx: Int = 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        selectedData = LocalDate.now()

        //인텐트로 넘어온 리스트 받기
        //val listData = intent.getSerializableExtra("mostBehavList") as List<ListData>
        //mostBehavList.addAll(listData)

        setMonthView()

        binding.preBtn.setOnClickListener {
            selectedData = selectedData.minusMonths(1)

            setMonthView()
        }
        binding.nextBtn.setOnClickListener {
            selectedData = selectedData.plusMonths(1)

            setMonthView()
        }
        binding.calendarGoMainIv.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView(){

        binding.monthText.text = monthYearfromDate(selectedData)
        val mostBehaviorList = MainActivity.mostBehavList
        val yearMonthText = yearMonthfromDate(selectedData)
        val dayList = dayInMonthArray(selectedData)
        val adapter = CalendarAdapter(sharedPreferences, dayList, this, yearMonthText, mostBehaviorList)

        var manager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext,7)
        Log.d("mostBehaviorList In Calendar",mostBehaviorList.toString())
        binding.recylerView.layoutManager = manager
        binding.recylerView.adapter = adapter


    }
    /*
    private fun mostBehavior() {
        Log.d("MostBehavior()", "메소드")

        val authService = AuthService()
        authService.setMostBehaviorView(this)
        Log.d("CalendarActivity / dog_idx", dog_idx.toString())
        authService.mostBehavior(dog_idx)
    }

    override fun onMostBehaviorSuccess(data: List<ListData>) {
        mostBehavList.addAll(data)
        Log.d("리스트 데이터", data.toString())
    }

    override fun onMostBehaviorFailure() {
        Log.d("Calendar", "실패")
    }
    */


    @RequiresApi(Build.VERSION_CODES.O)
    private fun monthYearfromDate(data: LocalDate): String{
        var formatter = DateTimeFormatter.ofPattern("MM월")
        return data.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun yearMonthfromDate(data: LocalDate): String{
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-")
        return data.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dayInMonthArray(date: LocalDate): ArrayList<String>{

        var numOfBlank = 0
        var dayList = ArrayList<String>()
        var yearMonth = YearMonth.from(date)
        var firstDay = selectedData.withDayOfMonth(1)
        var lastDay = yearMonth.lengthOfMonth()
        var dayOfWeek = firstDay.dayOfWeek.value

        for(i in 1..41){
            if(i <= dayOfWeek || i > (lastDay + dayOfWeek)){
                dayList.add("")
                numOfBlank += 1
                if (i  <= 7 && numOfBlank == 7){
                    dayList.clear()
                    numOfBlank = 0
                }
            }else{
                dayList.add((i - dayOfWeek).toString())
            }
        }
        return dayList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(day_Text: String) {
        var yearMonthDay = yearMonthfromDate(selectedData)+" "+ day_Text + "일"

        Toast.makeText(this,yearMonthDay, Toast.LENGTH_SHORT).show()
    }

}