package com.example.remid.services


import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.remid.R
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar


class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let {
            val medicineName = intent?.getStringExtra("med_name") ?: ""
            val dose  = intent?.getStringExtra("dose") ?: ""
            val hour = intent?.getIntExtra("hour", 8)
            val minute = intent?.getIntExtra("minute", 0)
            val requestCode = intent?.getIntExtra("request_code", 0)

            val builder = NotificationCompat.Builder(context,"medicine_ch")
                .setSmallIcon(R.drawable.icon_remi)
                .setContentTitle("Time to take medicine")
                .setContentText("Take $medicineName of $dose now")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)



            val hasPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context, android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true // No permission needed below Android 13
            }

            if (hasPermission) {
                // for jetpack compose
                NotificationManagerCompat.from(context).notify(requestCode ?: 0,builder.build())
            } else {
                Log.w("NotificationReceiver", "Notification permission not granted")
            }


            var remindTime = LocalDateTime.now().withHour(hour ?: 0).withMinute(minute ?: 0).withSecond(0)


                remindTime = remindTime.plusDays(1)



            val newIntent = Intent(context, NotificationReceiver::class.java).apply {
                putExtra("med_name", medicineName)
                putExtra("dose", dose)
                putExtra("request_code", requestCode)
                putExtra("hour", hour)
                putExtra("minute", minute)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode ?: 0,
                newIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                remindTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                pendingIntent
            )

        }




    }
}
