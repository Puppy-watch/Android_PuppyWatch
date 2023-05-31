package com.example.puppywatch

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.puppywatch.databinding.ActivityMyPageBinding
import com.example.puppywatch.response.Dog
import com.example.puppywatch.response.User
import com.example.puppywatch.view.MypageView

class MyPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        mypage()

        binding.myPageGoMainIv.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }
//    private fun mypage() {
//        val dogName = binding.myPagePuppyNameEt.text.toString()
//        val dogAge = binding.myPagePuppyAgeEt.text.toString().toInt()
//        val dogWeight = binding.myPagePuppyWeightEt.text.toString().toDouble()
//        val firstTime = binding.myPagePuppyBreakfastEt.text.toString()
//        val secondTime = binding.myPagePuppyLunchEt.text.toString()
//        val thirdTime = binding.myPagePuppyDinnerEt.text.toString()
//
//        val authService = AuthService()
//        authService.setMypageView(this)
//        authService.mypage(1, Dog(dogName,dogAge,dogWeight,firstTime,secondTime,thirdTime))
//    }
//    override fun onMypageSuccess() {
//    }
//
//    override fun onMypageFailure() {
//    }
}