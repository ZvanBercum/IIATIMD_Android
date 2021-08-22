package com.example.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.android.Resources.Database.AppDatabase
import com.example.android.Resources.Database.Medicine.Dosage
import com.example.android.Resources.Database.Medicine.Medicine
import kotlinx.android.synthetic.main.activity_make_dosage.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Month
import java.util.*

class MakeDosageActivity : AppCompatActivity() {

    lateinit var beginDatePicker: DatePicker
    lateinit var endDatePicker: DatePicker
    lateinit var timePicker: TimePicker
    lateinit var intervalSpinner: Spinner
    lateinit var dosageDosage: EditText
    var medId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_dosage)

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
}