package com.example.puppywatch

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.puppywatch.databinding.ActivityMainBinding
import com.example.puppywatch.response.ListData
import com.example.puppywatch.view.MostBehaviorView
import com.example.puppywatch.view.NowBehaviorView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUserMetadata
import com.google.firebase.messaging.FirebaseMessaging
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.util.*
import kotlin.collections.ArrayList
import com.google.firebase.FirebaseApp
import java.io.Serializable

class MainActivity : AppCompatActivity(), NowBehaviorView, MostBehaviorView {

    private lateinit var binding: ActivityMainBinding
    lateinit var  selectedData: LocalDate

    private lateinit var timer: Timer
    private lateinit var fetchTask: TimerTask

    //val mostBehavList : MutableList<ListData> = mutableListOf()

    //firebase
    val TAG : String = "hi"

    companion object {
        var dog_idx: Int = 0
        val mostBehavList : MutableList<ListData> = mutableListOf()
    }
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase 초기화
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this)
        }

        selectedData = LocalDate.now()

        //firebase
        if (FirebaseApp.getApps(this).isEmpty()){
            FirebaseApp.initializeApp(this)
        }
        //등록 토큰 가져오기
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful){
                Log.d("Token",task.exception.toString())
                return@OnCompleteListener
            }
            val token = task.result
            Log.d("token",token)

            val msg = getString(R.string.msg_token_fmt,token)
//            Toast.makeText(baseContext,msg,Toast.LENGTH_SHORT).show()

        })


        // dog_idx 가져오기
        val sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        dog_idx = sharedPreferences.getInt("dog_idx", 0)
        Log.d("MainActivity / dog_idx", dog_idx.toString())

        weekView()
        mostBehavior()

        // 주기적으로 nowBehavior() 호출하는 타이머 설정
        fetchTask = object : TimerTask() {
            override fun run() {
                nowBehavior()
            }
        }
        timer = Timer()
        timer.schedule(fetchTask, 0, 5000) // 0초 후부터 5초마다 실행

        binding.mainGoMyPageIv.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        binding.mainGoBehaviorIv.setOnClickListener {
            val intent = Intent(this, BehaviorActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        binding.goCalendarBtn.setOnClickListener{
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }
    }

    // 주간 달력 만들기
    @RequiresApi(Build.VERSION_CODES.O)
    fun weekView(){

        val cal = Calendar.getInstance()
        val df: DateFormat = SimpleDateFormat("dd")
        val currentDate = cal.time
        val date_week = df.format(currentDate)

        val nWeek: Int = cal.get(Calendar.DAY_OF_WEEK)
        val day_list = ArrayList<String>()

        val month_of_year = cal.get(Calendar.MONTH)+1
        val week_of_month = cal.get(Calendar.WEEK_OF_MONTH)

        //N월 N째주
        binding.weekday.text = month_of_year.toString() + "월 "+week_of_month.toString()+"번째 주"

        for (i in 0..14) {
            val calendar = cal.clone() as Calendar
            calendar.add(Calendar.DATE, i-7)
            val date_data = calendar.time
            val formattedDate = df.format(date_data)
            day_list.add(formattedDate)
        }

        val dayList = dayInMonthArray(selectedData)
        val dayOfToday= dayList.indexOf(date_week)

        val id_list = ArrayList<TextView>()
        id_list.add(binding.mainDate1)
        id_list.add(binding.mainDate2)
        id_list.add(binding.mainDate3)
        id_list.add(binding.mainDate4)
        id_list.add(binding.mainDate5)
        id_list.add(binding.mainDate6)
        id_list.add(binding.mainDate7)

        if (nWeek == 1) {
            for (i in 0..6) {
                id_list[i].text = dayList[dayOfToday + i]
            }
        } else if (nWeek == 7) {
            for (i in 0..6) {
                id_list[6 - i].text = dayList[dayOfToday - i]
            }
        } else {
            for (i in 0 until nWeek) {
                id_list[i].text = dayList[dayOfToday - nWeek + i + 1]
            }
            for (i in 0 until 7 - nWeek) {
                id_list[nWeek + i].text = dayList[dayOfToday + i + 1]
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dayInMonthArray(date: LocalDate): ArrayList<String> {

        var dayList = ArrayList<String>()
        var yearMonth = YearMonth.from(date)
        var firstDay = date.withDayOfMonth(1)
        var lastDay = yearMonth.lengthOfMonth()
        var dayOfWeek = firstDay.dayOfWeek.value

        for (i in 1..41) {
            if (i <= dayOfWeek || i > (lastDay + dayOfWeek)) {
                dayList.add("")
            } else {
                dayList.add((i - dayOfWeek).toString())
            }
        }
        return dayList
    }

    private fun nowBehavior() {
        Log.d("NowBehavior()", "메소드")

        val authService = AuthService()
        authService.setNowBehaviorView(this)
        authService.nowBehavior(dog_idx)
    }

    private fun mostBehavior() {
        Log.d("MostBehavior()", "메소드")

        val authService = AuthService()
        authService.setMostBehaviorView(this)
        authService.mostBehavior(dog_idx)
    }

    override fun onNowBehaviorSuccess(nowBehav: String) {
        Log.d("nowBehav 현재 행동", nowBehav)
        binding.mainPuppyBehaviorTv.setText("현재 " + nowBehav + " 행동 중")
        changeIcon(nowBehav)
        Log.d("NowBehaviorSuccess", "성공")
    }
    override fun onNowBehaviorFailure() {
        Log.d("NowBehaviorFailure", "실패")
    }

    override fun onMostBehaviorSuccess(data: List<ListData>) {
        Log.d("MostBehaviorSuccess", "성공")
        makeWeekIcon(data)
        mostBehavList.addAll(data)
        Log.d("MostBehaviorListSuccess", mostBehavList.toString())
    }
    override fun onMostBehaviorFailure() {
        Log.d("MostBehaviorFailure", "실패")
    }

    // 현재 행동 아이콘
    fun changeIcon(act:String) {
        when(act) {
            "run" -> binding.currentActImg.setImageResource(R.drawable.ic_main_run)
            "bite" -> binding.currentActImg.setImageResource(R.drawable.ic_main_bite)
            "sleep" -> binding.currentActImg.setImageResource(R.drawable.ic_main_lie)
            "stand" -> binding.currentActImg.setImageResource(R.drawable.ic_main_stand)
            "walk" -> binding.currentActImg.setImageResource(R.drawable.ic_main_walk)
            "seat" -> binding.currentActImg.setImageResource(R.drawable.ic_main_sit)
            "eat" -> binding.currentActImg.setImageResource(R.drawable.ic_main_eat)
            else -> binding.currentActImg.setImageResource(R.drawable.ic_main_walk)
        }
    }

    //배경이 주황색인 아이콘으로 변경
    fun changeOrangeIcon(act: String, iconId: ImageView) {
        when (act) {
            "eat" -> iconId.setImageResource(R.drawable.ic_cal_eat)
            "run" -> iconId.setImageResource(R.drawable.ic_cal_run)
            "bite" -> iconId.setImageResource(R.drawable.ic_cal_bite)
            "sleep" -> iconId.setImageResource(R.drawable.ic_cal_lie)
            "stand" -> iconId.setImageResource(R.drawable.ic_cal_stand)
            "seat" -> iconId.setImageResource(R.drawable.ic_cal_sit)
            else -> iconId.setImageResource(R.drawable.ic_cal_walk)
        }
    }

    //주간 아이콘 변경
    fun makeWeekIcon(data : List<ListData>){
        Log.d("test",data.toString())

        //MostBehaviorList[i].Date로 접근 가능
        val cal = Calendar.getInstance()
        val df: DateFormat = SimpleDateFormat("yyyy-MM-")
        val currentDate = cal.time
        val date = df.format(currentDate)

        var WeekTextViewList = ArrayList<TextView>()
        WeekTextViewList.add(binding.mainDate1)
        WeekTextViewList.add(binding.mainDate2)
        WeekTextViewList.add(binding.mainDate3)
        WeekTextViewList.add(binding.mainDate4)
        WeekTextViewList.add(binding.mainDate5)
        WeekTextViewList.add(binding.mainDate6)
        WeekTextViewList.add(binding.mainDate7)

        var WeekImageViewList = ArrayList<ImageView>()
        WeekImageViewList.add(binding.mainIcon1)
        WeekImageViewList.add(binding.mainIcon2)
        WeekImageViewList.add(binding.mainIcon3)
        WeekImageViewList.add(binding.mainIcon4)
        WeekImageViewList.add(binding.mainIcon5)
        WeekImageViewList.add(binding.mainIcon6)
        WeekImageViewList.add(binding.mainIcon7)

        var WeekList = ArrayList<String>()
        for (i in WeekTextViewList) {
            if ((i.getText().toString() != "") && (i.getText().toString().toInt() < 10)) {
                WeekList.add(date + "0" + i.getText().toString())
            } else {
                WeekList.add(date + i.getText().toString())
            }
        }
        Log.d("WeekList",WeekList.toString())
        //통계값에서 이번 주에 해당하는 날짜 값만
        for (item in data) {
            for (i in 0..6) {
                if ((WeekList[i] != "") && item.Date == WeekList[i]) {
                    Log.d("mostBehav: " + item.Date, item.mostBehav)
                    changeOrangeIcon(item.mostBehav, WeekImageViewList[i])
                }
            }
        }
    }
}