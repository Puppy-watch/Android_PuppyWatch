package com.example.puppywatch

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.puppywatch.databinding.ActivityMyPageBinding
import com.example.puppywatch.response.Dog
import com.example.puppywatch.response.DogInfoResponse
import com.example.puppywatch.response.ListData
import com.example.puppywatch.response.User
import com.example.puppywatch.view.DogInfoView
import com.example.puppywatch.view.MypageView

class MyPageActivity : AppCompatActivity(), MypageView, DogInfoView {

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
        dog_idx = sharedPreferences.getInt("dog_idx", 0)
        Log.d("MyPageActivity / dog_idx", dog_idx.toString())

        dogInfo()
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
            finish()
        }

        binding.myPageGoMainTv.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }

        binding.myPageEditCameraIv.setOnClickListener {
            // 갤러리 호출
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityResult.launch(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        // 이미지 URI를 SharedPreferences에서 복원
        val sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val uriString = sharedPreferences.getString("image_uri", null)
        val imageUri = uriString?.let { Uri.parse(it) }

        // 이미지 URI가 있을 경우에만 이미지 설정
        imageUri?.let {
            Glide.with(this)
                .load(it)
                .into(binding.myPagePuppyIv)
        }
    }

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        // 결과 코드
        if (result.resultCode == RESULT_OK && result.data != null) {
            val uri = result.data!!.data

            Glide.with(this)
                .load(uri)
                .into(binding.myPagePuppyIv)

            // 이미지 URI를 SharedPreferences에 저장
            val sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("image_uri", uri.toString())
            editor.apply()
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

    private fun dogInfo() {
        Log.d("DogInfo()", "메소드")

        val authService = AuthService()
        authService.setDogInfoView(this)
        authService.dogInfo(dog_idx)
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

    override fun onDogInfoSuccess(dogInfoList: DogInfoResponse) {
        Log.d("DogInfoSuccess", "성공")

        binding.myPagePuppyNameEt.setText(dogInfoList.dogName)
        binding.myPagePuppyAgeEt.setText(dogInfoList.dogAge.toString())
        binding.myPagePuppyWeightEt.setText(dogInfoList.dogWeight.toString())
        binding.myPagePuppyBreakfastEt.setText(dogInfoList.firstTime)
        binding.myPagePuppyLunchEt.setText(dogInfoList.secondTime)
        binding.myPagePuppyDinnerEt.setText(dogInfoList.thirdTime)
    }

    override fun onDogInfoFailure() {
        Log.d("DogInfoFailure", "실패")
    }

    override fun onMypageSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }

    override fun onMypageFailure() {
        Toast.makeText(this, "강아지 정보 수정에 실패했습니다.", Toast.LENGTH_SHORT).show()
    }
}