package com.johnmaricool.mario_designs.androidcomponents

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
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
    }

}
