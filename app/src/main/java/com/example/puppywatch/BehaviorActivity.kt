package com.example.puppywatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.puppywatch.databinding.ActivityBehaviorBinding
import com.example.puppywatch.response.AbnormalResponse
import com.example.puppywatch.view.AbnormalView

class BehaviorActivity : AppCompatActivity(), AbnormalView {

    private lateinit var binding: ActivityBehaviorBinding
//    val itemList = arrayListOf<BehaviorListItem>()      // 아이템 배열
//    val listAdapter = BehavoirAdapter(itemList)     // 어댑터

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBehaviorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.behaviorGoMainIv.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        binding.behaviorGoMainTv.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        // 레이아웃 매니저와 어댑터 설정
        binding.behaviorRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.behaviorRv.setHasFixedSize(true)
//        binding.behaviorRv.adapter = listAdapter
//
//        // 아이템 추가
//        itemList.add(BehaviorListItem("2023.01.02 13:00:12 - 이상행동", "new"))
//        itemList.add(BehaviorListItem("2023.01.12 13:00:12 - 이상행동", "new"))
//        itemList.add(BehaviorListItem("2023.01.22 13:00:12 - 이상행동", "new"))
//        itemList.add(BehaviorListItem("2023.01.32 13:00:12 - 이상행동", "new"))
//        itemList.add(BehaviorListItem("2023.01.42 13:00:12 - 이상행동", "new"))
//        itemList.add(BehaviorListItem("2023.01.52 13:00:12 - 이상행동", "new"))
//        itemList.add(BehaviorListItem("2023.03.02 13:00:12 - 이상행동", "new"))
//        itemList.add(BehaviorListItem("2023.03.12 13:00:12 - 이상행동", "new"))
//        itemList.add(BehaviorListItem("2023.03.22 13:00:12 - 이상행동", "new"))
//        itemList.add(BehaviorListItem("2023.03.32 13:00:12 - 이상행동", "new"))
//        itemList.add(BehaviorListItem("2023.03.42 13:00:12 - 이상행동", "new"))
//
//        // 리스트가 변경됨을 어댑터에 알림
//        listAdapter.notifyDataSetChanged()

        abnormal()
    }

    private fun abnormal() {
        Log.d("abnormal()", "메소드")

        val authService = AuthService()
        authService.setAbnormalView(this)
        authService.abnormal(1)
    }

    override fun onAbnormalSuccess(abnormalList: AbnormalResponse) {
        Log.d("onAbnormalSuccess", "성공")
        binding.behaviorRv.adapter = BehavoirAdapter(abnormalList.data)
    }

    override fun onAbnormalFailure() {
        Log.d("onAbnormalFailure", "실패")
    }
}