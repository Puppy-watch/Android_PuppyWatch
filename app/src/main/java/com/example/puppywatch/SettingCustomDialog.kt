package com.example.puppywatch

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.Button

class SettingCustomDialog (
    context: Context
) {
    private lateinit var onClickListener: OnDialogClickListener
    private val dialog = Dialog(context)

    fun setOnClickListener(listener: OnDialogClickListener)
    {
        onClickListener = listener
    }

    fun settingInitViews(){
        // 뒤로가기 버튼, 빈 화면 터치를 통해 dialog가 사라지지 않도록
        dialog.setContentView(R.layout.dialog_setting)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()

        // OK Button 클릭에 대한 Callback 처리. 이 부분은 상황에 따라 자유롭게!
        val settingNoBtn = dialog.findViewById<Button>(R.id.setting_no_btn)
        settingNoBtn.setOnClickListener {
            onClickListener.onClicked(false)
            dialog.dismiss()
        }

        val settingYesBtn = dialog.findViewById<Button>(R.id.setting_yes_btn)
        settingYesBtn.setOnClickListener {
            onClickListener.onClicked(true)
            dialog.dismiss()
        }

    }

    interface OnDialogClickListener
    {
        fun onClicked(flag: Boolean)
    }
}