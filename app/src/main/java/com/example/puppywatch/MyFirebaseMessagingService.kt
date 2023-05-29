package com.example.puppywatch

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService :  FirebaseMessagingService() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage : RemoteMessage) {
        Log.d("fire test1", "firebase message received")
        schduleJob()

        if (remoteMessage.data.isNotEmpty()){
            Log.d("fire test2",remoteMessage.data.toString())
        }
        /*
        remoteMessage.notification?.let{
            Log.d("fire test3", remoteMessage.)
        }

        remoteMessage.data.isNotEmpty().let { hasData ->
            if (hasData) {
                // 메시지 데이터 읽기
                val data = remoteMessage.data
                val messageBody = data["body"] // 메시지 내용
                val messageTitle = data["title"] // 메시지 제목

                // 메시지 내용과 제목을 사용하여 처리하는 작업 수행
            }
        }*/

        // 알림 확인
        remoteMessage.notification?.let { notification ->
            val title = notification.title // 알림 제목
            val body = notification.body // 알림 내용
            // 알림 내용을 사용하여 알림을 표시하거나 처리하는 등의 작업 수행
            Log.d("fire test_title",body.toString())
            //여기서 알림창 띄워야 할 듯.
        }


    }
    //토큰 처음 생성될 때 토큰 검색
    override fun onNewToken(token: String){
        Log.d("fire test4", "firebase message received")
        sendRegisterationToServer(token)
    }

    private fun schduleJob() {
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
        WorkManager.getInstance(this).beginWith(work).enqueue()
    }

    private fun sendRegisterationToServer(token: String?){
        Log.d("fire test5", "firebase message received")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification(messageBody: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Android 8.0 (Oreo) 이상에서는 알림 채널을 생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                getString(R.string.default_notification_channel_id),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        private const val TAG = "MyfirebaseService"
    }

}