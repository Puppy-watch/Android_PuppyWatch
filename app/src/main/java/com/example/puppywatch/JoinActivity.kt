package com.example.puppywatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.puppywatch.databinding.ActivityJoinBinding
import java.util.regex.Pattern

class JoinActivity : AppCompatActivity(), JoinView {

    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.joinSubmitBtn.setOnClickListener {
            join()
        }

        binding.joinGoLoginTv.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        binding.joinPasswordEt.setOnClickListener {
            if(isRegularPW(binding.joinPasswordEt.text.toString()) == true) {
                binding.joinPasswordFailTv.text = "올바른 비밀번호 입니다."
            }
            else {
                binding.joinPasswordFailTv.text = "올바른 비밀번호가 아닙니다."
            }
        }

        binding.joinPasswordCheckEt.setOnClickListener{
            if(binding.joinPasswordEt.text.toString() == binding.joinPasswordCheckEt.text.toString()) {
                binding.joinPasswordCheckFailTv.text = "비밀번호가 일치합니다"
                binding.joinPasswordCheckFailTv.visibility = View.VISIBLE
            }
            else {
                binding.joinPasswordCheckFailTv.text = "비밀번호가 일치하지 않습니다"
                binding.joinPasswordCheckFailTv.visibility = View.VISIBLE
            }
        }
    }

    private fun getUser(): UserSign {
        val userId: String = binding.joinIdEt.text.toString()
        val userPw: String = binding.joinPasswordEt.text.toString()
        val userName: String = binding.joinUserNameEt.text.toString()
        val dogName: String = binding.joinPuppyNameEt.text.toString()

        return UserSign(userId, userPw, userName, dogName)
    }

    private fun join() {
        Log.d("JOIN()", "메소드")

        if(binding.joinIdEt.text.toString().isEmpty()) {
            return
        }

        if(isRegularPW(binding.joinPasswordEt.text.toString()) == false) {
            return
        }

        if(binding.joinPasswordEt.text.toString() != binding.joinPasswordCheckEt.text.toString()) {
            return
        }

        if(binding.joinUserNameEt.text.toString().isEmpty()) {
            return
        }

        if(binding.joinPuppyNameEt.text.toString().isEmpty()) {
            return
        }

        val authService = AuthService()
        authService.setJoinView(this)
        authService.join(getUser())
    }

    private fun clearInputText() {
        binding.joinIdEt.setText("")
        binding.joinPasswordEt.setText("")
        binding.joinUserNameEt.setText("")
        binding.joinPuppyNameEt.setText("")
        binding.joinPasswordCheckFailTv.visibility = View.INVISIBLE
    }

    // JoinView 상속
    override fun onJoinSuccess() {
        clearInputText()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }

    // 여기로 안 넘어옴..
    override fun onJoinFailure() {
        Toast.makeText(this, "회원가입 실패했습니다.", Toast.LENGTH_SHORT).show()
        clearInputText()
    }

    private fun isRegularPW(password: String): Boolean {
        val pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?^&.])[A-Za-z[0-9]$@$!%*#?^&.]{6,15}$"
        val pattern = Pattern.compile(pwPattern)
        val matcher = pattern.matcher(pwPattern)
        Log.d("Match", matcher.find().toString())

        return (Pattern.matches(pwPattern, password))
    }
}