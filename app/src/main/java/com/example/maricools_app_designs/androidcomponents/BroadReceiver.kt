package com.example.maricools_app_designs.androidcomponents

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.maricools_app_designs.R

class BroadReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
       showNotification(context)
    }

    private fun showNotification(context: Context) {
        val manager = NotificationManagerCompat.from(context)
        val builder = NotificationCompat.Builder(context, MyApplication.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Know your Faith")
                .setContentText("Answer Bible and catholic quiz question")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent(context))
                .build()

        manager.notify(1, builder)
    }

    private fun pendingIntent(context: Context): PendingIntent {
        return NavDeepLinkBuilder(context)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.catholicQuizFragment)
                .createPendingIntent()
    }
}
