package com.example.maricools_app_designs.androidcomponents

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import com.example.maricools_app_designs.WorkerClass
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                    CHANNEL_ID,
                "Answer Quiz",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Know your Catholic Faith"

            val nM = getSystemService(NotificationManager::class.java)
            nM.createNotificationChannel(channel)
        }
    }

    companion object{
        val CHANNEL_ID = "display daily saint"
        val receiverFilter = "maricool_know_your_faith_an_hour_passed"
    }

}
