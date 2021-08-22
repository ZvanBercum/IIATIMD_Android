package com.example.android.Resources.Reminder

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.android.R

public class ReminderBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val builder = NotificationCompat.Builder(context, "com.example.android")
            .setSmallIcon(R.drawable.ic_launcher_round)
            .setContentTitle("Medicine Reminder")
            .setContentText("Vergeet niet uw medicijnen in te nemen!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(100, builder.build())

    }

}