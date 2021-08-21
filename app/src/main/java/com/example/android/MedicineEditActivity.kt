package com.example.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.contextaware.withContextAvailable
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.android.Resources.Database.AppDatabase
import com.example.android.Resources.Database.Medicine.Medicine
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

class MedicineEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_edit)
        val id:Long = intent.getStringExtra("medicine").toLong()
        if (id != null) {
            val nameEdit = findViewById<EditText>(R.id.MedEditName)
            val descEdit = findViewById<EditText>(R.id.MedEditDesc)
            nameEdit.setText("hallo")
            runBlocking {
                val medicine = getMedicine(id)
                if (medicine != null) {
                    nameEdit.setText(medicine.name)
                    descEdit.setText(medicine.desc)
                }
            }
        }else{
            switchToHome()
        }
    }

    suspend fun getMedicine(id: Long): Medicine? {
        return AppDatabase.getDatabase(applicationContext).medicineDao().findMedicineById(id)
    }

    /**
     * @func switchToHome
     * Load the home screen if user is logged
     */
    private fun switchToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}