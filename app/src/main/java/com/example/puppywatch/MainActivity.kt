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

        //n월 n번째주
        var month_of_year = cal.get(Calendar.MONTH)+1
        val week_of_month = cal.get(Calendar.WEEK_OF_MONTH)

        //날짜 담을 리스트
        var day_list = ArrayList<String>()
        var first_week = ArrayList<String>()
        var second_week = ArrayList<String>()
        var third_week = ArrayList<String>()
        var fourth_week = ArrayList<String>()
        var fifth_week = ArrayList<String>()
        var sixth_week = ArrayList<String>()

        //N월 N째주
        if (month_of_year == 13){
            month_of_year = 1
        }
        binding.weekday.text = month_of_year.toString() + "월 "+week_of_month.toString()+"번째 주"

        //위클리 텍스트뷰 리스트에 넣기
        val weekText_list = ArrayList<TextView>()
        weekText_list.add(binding.mainDate1)
        weekText_list.add(binding.mainDate2)
        weekText_list.add(binding.mainDate3)
        weekText_list.add(binding.mainDate4)
        weekText_list.add(binding.mainDate5)
        weekText_list.add(binding.mainDate6)
        weekText_list.add(binding.mainDate7)

        day_list = dayInMonthArray(selectedData)

        for(i in 0..6){
            first_week.add(day_list[i])
            second_week.add(day_list[i+7])
            third_week.add(day_list[i+14])
            fourth_week.add(day_list[i+21])
            fifth_week.add(day_list[i+28])
            //sixth_week.add(day_list[i+35])
        }
        Log.d("first",first_week.toString())
        Log.d("second",second_week.toString())
        Log.d("third",third_week.toString())
        Log.d("fourth",fourth_week.toString())
        Log.d("fifth",fifth_week.toString())
        //Log.d("sixth",sixth_week.toString())

        //몇째주인지 체크해서 그 주의 위클리 캘린더 보냄
        when (week_of_month){
            1 -> {
                for (i in 0..6){
                    weekText_list[i].text = first_week[i]
                }
            }
            2 -> {
                for (i in 0..6){
                    weekText_list[i].text = first_week[i]
                }
            }
            3 -> {
                for (i in 0..6){
                    weekText_list[i].text = second_week[i]
                }
            }
            4 -> {
                for (i in 0..6){
                    weekText_list[i].text = third_week[i]
                }
            }
            5 -> {
                for (i in 0..6){
                    weekText_list[i].text = fourth_week[i]
                }
            }
            else -> {
                for (i in 0..6){
                    weekText_list[i].text = fifth_week[i]
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dayInMonthArray(date: LocalDate): ArrayList<String> {

        var dayList = ArrayList<String>()
        var yearMonth = YearMonth.from(date)
        var firstDay = date.withDayOfMonth(1)
        var lastDay = yearMonth.lengthOfMonth()
        //1일의 요일값
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