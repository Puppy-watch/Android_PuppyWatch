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
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService :  FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage : RemoteMessage) {
        Log.d("fire test1", "firebase message received")

        if (remoteMessage.data.isNotEmpty()){
            Log.d("fire test2",remoteMessage.data.toString())
        }
        remoteMessage.notification?.let{
            Log.d("fire test3", "firebase message received")
        }

    }
    //토큰 처음 생성될 때 토큰 검색
    override fun onNewToken(token: String){
        Log.d("fire test4", "firebase message received")
        sendRegisterationToServer(token)
    }

    private fun sendRegisterationToServer(token: String?){
        Log.d("fire test5", "firebase message received")
    }
    private fun sendNotification(messageBody: String){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)

        val chnnelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this,chnnelId).setSmallIcon(R.drawable.ic_cal_eat)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0,notificationBuilder.build())
    }
    companion object {
        private const val TAG = "MyfirebaseService"
    }

}