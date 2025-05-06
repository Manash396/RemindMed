package com.example.remid.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import androidx.compose.ui.text.font.FontVariation.Settings
import androidx.core.content.ContextCompat.startActivity
import com.example.remid.data.Medicine
import java.time.LocalDateTime
import java.time.ZoneId

class AlarmScheduler(
    private val context : Context
) : Scheduler {

    override fun schedule(medicine: Medicine) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager


        medicine.timeTable.forEachIndexed { index, timeString ->
            val (hour, min) = timeString.split(":").map { it.toInt() }
            var remindTime = LocalDateTime.now().withHour(hour).withMinute(min).withSecond(0)

            if (remindTime.isBefore(LocalDateTime.now())) {
                remindTime = remindTime.plusDays(1)
            }

            val requestcode = (medicine.name+hour+min).hashCode()

            val intent =
                Intent(context, NotificationReceiver::class.java).apply {
                    putExtra("med_name", medicine.name)
                    putExtra("dose", medicine.dosage)
                    putExtra("request_code", requestcode)
                    putExtra("hour", hour)
                    putExtra("minute", min)
                }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestcode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                remindTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                pendingIntent
            )

        }
    }

}