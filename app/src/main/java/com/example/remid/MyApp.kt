package com.example.remid

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Check if the Android version is Oreo (API level 26) or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val channelId = "medicine_ch"
            val channelName = "remid"
            val description = "Channel for medicine dose notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH

            // Create a NotificationChannel
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                this.description = description
            }

            // Get NotificationManager service and create the channel
            val notificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

