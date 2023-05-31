package com.example.puppywatch

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.puppywatch.databinding.ActivityMyPageBinding
import com.example.puppywatch.response.Dog
import com.example.puppywatch.response.ListData
import com.example.puppywatch.response.User
import com.example.puppywatch.view.MypageView

class MyPageActivity : AppCompatActivity(), MypageView {

    private lateinit var binding: ActivityMyPageBinding

    companion object {
        var dog_idx: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // dog_idx 가져오기
        val sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        MainActivity.dog_idx = sharedPreferences.getInt("dog_idx", 0)
        Log.d("MainActivity / dog_idx", MainActivity.dog_idx.toString())

        enableFalse()

        binding.myPageEditIv.setOnClickListener {
            val dialog = SettingCustomDialog(this)
            dialog.settingInitViews()
            dialog.setOnClickListener(object : SettingCustomDialog.OnDialogClickListener{
                override fun onClicked(flag: Boolean) {
                    if (flag) {
                        enableTrue()
                        binding.myPageEditButton.visibility = View.VISIBLE
                    }
                }
            })
        }

        binding.myPageEditButton.setOnClickListener {
            mypage()
        }

        binding.myPageGoMainIv.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }

    private fun enableFalse() {
        binding.myPagePuppyNameEt.isEnabled = false
        binding.myPagePuppyAgeEt.isEnabled = false
        binding.myPagePuppyWeightEt.isEnabled = false
        binding.myPagePuppyBreakfastEt.isEnabled = false
        binding.myPagePuppyLunchEt.isEnabled = false
        binding.myPagePuppyDinnerEt.isEnabled = false
    }

    private fun enableTrue() {
        binding.myPagePuppyNameEt.isEnabled = true
        binding.myPagePuppyAgeEt.isEnabled = true
        binding.myPagePuppyWeightEt.isEnabled = true
        binding.myPagePuppyBreakfastEt.isEnabled = true
        binding.myPagePuppyLunchEt.isEnabled = true
        binding.myPagePuppyDinnerEt.isEnabled = true
    }

    private fun getDog(): Dog {
        val dogName: String = binding.myPagePuppyNameEt.text.toString()
        val dogAge = binding.myPagePuppyAgeEt.text.toString().toInt()
        val dogWeight = binding.myPagePuppyWeightEt.text.toString().toDouble()
        val firstTime = binding.myPagePuppyBreakfastEt.text.toString() + ":00"
        val secondTime = binding.myPagePuppyLunchEt.text.toString() + ":00"
        val thirdTime = binding.myPagePuppyDinnerEt.text.toString() + ":00"

        return Dog(dogName, dogAge, dogWeight, firstTime, secondTime, thirdTime)
    }

    private fun mypage() {
        if(binding.myPagePuppyNameEt.toString().isEmpty()) {
            Toast.makeText(this, "반려견 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.myPagePuppyAgeEt.toString().isEmpty()) {
            Toast.makeText(this, "반려견 나이을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.myPagePuppyWeightEt.toString().isEmpty()) {
            Toast.makeText(this, "반려견 몸무게를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val authService = AuthService()
        authService.setMypageView(this)
        authService.mypage(dog_idx, getDog())
    }
    override fun onMypageSuccess() {
        binding.myPagePuppyNameEt.setText(binding.myPagePuppyNameEt.text.toString())
        binding.myPagePuppyAgeEt.setText(binding.myPagePuppyAgeEt.text.toString().toInt())
        binding.myPagePuppyWeightEt.setText(binding.myPagePuppyWeightEt.text.toString())
        binding.myPagePuppyBreakfastEt.setText(binding.myPagePuppyBreakfastEt.text.toString())
        binding.myPagePuppyLunchEt.setText(binding.myPagePuppyLunchEt.text.toString())
        binding.myPagePuppyDinnerEt.setText(binding.myPagePuppyDinnerEt.text.toString())

        binding.myPageEditButton.visibility = View.INVISIBLE
        enableFalse()

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }

    override fun onMypageFailure() {
        Toast.makeText(this, "강아지 정보 수정에 실패했습니다.", Toast.LENGTH_SHORT).show()
    }
}