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
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.Resources.Adapters.ReminderAdapter
import com.example.android.Resources.Database.AccessDatabase
import com.example.android.Resources.Medicine.Medicine
import com.example.android.Resources.Reminder.Reminder
import kotlinx.android.synthetic.main.activity_reminder.*

class ReminderActivity : AppCompatActivity()  {

    private lateinit var notificationManager : NotificationManager
    private lateinit var notificationChannel : NotificationChannel
    private lateinit var builder : Notification.Builder
    private lateinit var linearLayoutManager : LinearLayoutManager
    private lateinit var adapter : ReminderAdapter


    private val reminders : ArrayList<Reminder> = arrayListOf()
    private val channelId = "com.example.android"
    private val description = "Medicine reminder notification"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "medicineDB").allowMainThreadQueries().build()

        reminders.add(Reminder("1",  "08:00", "14-08-2020", "1"))
        reminders.add(Reminder("2",  "12:00", "14-08-2020", "1"))
        reminders.add(Reminder("3",  "16:00", "14-08-2020", "1"))
        reminders.add(Reminder("4",  "08:00", "15-08-2020", "1"))
        reminders.add(Reminder("5",  "12:00", "15-08-2020", "1"))
        reminders.add(Reminder("6",  "16:00", "15-08-2020", "1"))

        adapter = ReminderAdapter(reminders)
        recyclerView.adapter = adapter

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager





        //DO NOT EDIT ABOVE THIS LINE ----
        notification.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_UPDATE_CURRENT)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                notificationChannel = NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this, channelId)
                    .setContentTitle("Medicine reminder")
                    .setContentText("Test notificatie")
                    .setSmallIcon(R.drawable.ic_launcher_round)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher))
                    .setContentIntent(pendingIntent)
            } else {
                builder = Notification.Builder(this, channelId)
                    .setContentTitle("Medicine reminder")
                    .setContentText("Test notificatie")
                    .setSmallIcon(R.drawable.ic_launcher_round)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher))
                    .setContentIntent(pendingIntent)
            }
            notificationManager.notify(1234,builder.build())
        }

    }

}