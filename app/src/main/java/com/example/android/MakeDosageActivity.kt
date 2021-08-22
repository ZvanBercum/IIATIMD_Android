package com.example.android

import android.app.*
import android.app.AlarmManager.RTC_WAKEUP
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.android.Resources.Database.AppDatabase
import com.example.android.Resources.Database.Medicine.Dosage
import com.example.android.Resources.Database.Medicine.Medicine
import com.example.android.Resources.Reminder.ReminderBroadcastReceiver
import kotlinx.android.synthetic.main.activity_make_dosage.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.reflect.Array.set
import java.time.Month
import java.util.*

class MakeDosageActivity : AppCompatActivity() {

    lateinit var beginDatePicker: DatePicker
    lateinit var endDatePicker: DatePicker
    lateinit var timePicker: TimePicker
    lateinit var intervalSpinner: Spinner
    lateinit var dosageDosage: EditText
    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent: PendingIntent
    private lateinit var notificationManager : NotificationManager
    private lateinit var notificationChannel : NotificationChannel
    private lateinit var builder : Notification.Builder

    private val channelId = "com.example.android"
    private val description = "Medicine reminder notification"
    var medId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_dosage)
        createNotificationChannel()

        dosageDosage = findViewById(R.id.dosageMeasure)
        beginDatePicker = findViewById(R.id.begindate)
        endDatePicker = findViewById(R.id.enddate)
        timePicker = findViewById(R.id.timeselect)
        intervalSpinner = findViewById(R.id.selectinterval)

        val saveBtn = findViewById<Button>(R.id.saveDosages)

        val intervalArray = arrayOf("Nooit", "Dagelijks", "Wekelijks", "Maandelijks", "Jaarlijks")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, intervalArray)
        intervalSpinner.adapter = adapter

        timePicker.setIs24HourView(true)

        medId = intent.getStringExtra("medId").toLong()
        if (medId == null || medId == 0.toLong()) {
            switchToHome()
            return
        }

        saveBtn.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                createDosages()
            }
        }
    }

    private fun getDateFromPicker(picker: DatePicker, hour: Int, minutes: Int): Long{
        val day = picker.dayOfMonth
        val month = picker.month
        val year = picker.year

        val cal = Calendar.getInstance()
        cal.set(year, month, day, hour, minutes)
        return cal.timeInMillis / 1000
    }

    private suspend fun createDosages(){
        if(dosageDosage.text.toString().isNullOrEmpty()){
            return
        }
        val hour = timePicker.hour
        val minute = timePicker.minute
        var beginDate = getDateFromPicker(beginDatePicker, hour, minute)
        val endDate = getDateFromPicker(endDatePicker, hour, minute)
        val interval = intervalSpinner.selectedItem
        while(beginDate <= endDate){
            var dosage = Dosage(
                medicineId = medId,
                dosage = dosageDosage.text.toString(),
                date = beginDate
            )
            AppDatabase.getDatabase(applicationContext).dosageDao().insert(dosage)
            val intent = Intent(this, ReminderBroadcastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                beginDate*1000,
                pendingIntent)

            val cal: Calendar = Calendar.getInstance()
            //Convert Epoch Unix Timestamp from seconds to milliseconds
            cal.timeInMillis = beginDate*1000
            when(interval){
                "Nooit" -> break
                "Dagelijks" -> beginDate+=86400
                "Wekelijks" -> beginDate+= 604800
                "Maandelijks" -> {
                    cal.add(Calendar.MONTH, 1)
                    beginDate = cal.timeInMillis*1000
                }
                "Jaarlijks" -> {
                    cal.add(Calendar.YEAR, 1)
                    beginDate = cal.timeInMillis*1000
                }
                else -> break
            }
        }
        switchToMedicine()
    }

    private fun switchToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun switchToMedicine(){
        val intent = Intent(this, MedicineEditActivity::class.java)
        intent.putExtra("medicine", medId.toString())
        this.startActivity(intent)
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "MedicineReminderChannel"
            val description = "Channel for Medicine Reminder"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("com.example.android", name, importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}