package com.example.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.android.Resources.Database.AppDatabase
import com.example.android.Resources.Database.Dosage.DosageListFragment
import com.example.android.Resources.Database.Medicine.Medicine
import kotlinx.coroutines.runBlocking

class MedicineEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_edit)
        val id:Long = intent.getStringExtra("medicine").toLong()
        if (id == null) {
            switchToHome()
            return
        }
        val nameEdit = findViewById<EditText>(R.id.MedEditName)
        val descEdit = findViewById<EditText>(R.id.MedEditDesc)
        val saveBtn = findViewById<Button>(R.id.save)
        val removeBtn = findViewById<TextView>(R.id.medicineRemove)

        runBlocking {
            val medicine = getMedicine(id)
            if (medicine != null) {
                nameEdit.setText(medicine.name)
                descEdit.setText(medicine.desc)
                saveBtn.setOnClickListener {
                    medicine.name = nameEdit.text.toString()
                    medicine.desc = descEdit.text.toString()
                    runBlocking{
                        saveMedicine(medicine)
                    }
                }
                removeBtn.setOnClickListener {
                    runBlocking{
                        deleteMedicine(medicine.id)
                    }
                }
            }
        }
        val fragment = DosageListFragment.newInstance(id)
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentHolder2, fragment)
        fragmentTransaction.commitNow()
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

    private fun switchToIndex(){
        val intent = Intent(this, MedicineActivity::class.java)
        startActivity(intent)
    }

    private suspend fun saveMedicine(medicine: Medicine){
        AppDatabase.getDatabase(applicationContext).medicineDao().update(medicine)
        Toast.makeText(applicationContext, "Medicijn is opgeslagen", Toast.LENGTH_SHORT).show()
    }

    private suspend fun deleteMedicine(id: Long){
        AppDatabase.getDatabase(applicationContext).medicineDao().deleteById(id)
        switchToHome()
    }
}