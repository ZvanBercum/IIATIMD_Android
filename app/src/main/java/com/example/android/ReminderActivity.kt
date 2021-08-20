package com.example.android

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_reminder.*

class ReminderActivity : AppCompatActivity()  {
    private var count: Int = 0
    private lateinit var notificationManager : NotificationManager
    private lateinit var notificationChannel : NotificationChannel
    private lateinit var builder : Notification.Builder
    private lateinit var linearLayoutManager : LinearLayoutManager

    private val channelId = "com.example.android"
    private val description = "Medicine reminder notification"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        linearLayoutManager = LinearLayoutManager(this)
        reminderRecyclerView.layoutManager = linearLayoutManager

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notification.setOnClickListener {
            count++
            notification(count, "Medicine reminder", "Test notificatie")
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun notification(id: Int, title: String, text: String){
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel = NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher))
                .setContentIntent(pendingIntent)
        } else {
            builder = Notification.Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher))
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(id, builder.build())
    }

}