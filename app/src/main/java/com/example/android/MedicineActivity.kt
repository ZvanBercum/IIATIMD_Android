package com.example.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.android.Resources.Database.AppDatabase
import com.example.android.Resources.Database.Medicine.Medicine
import com.example.android.Resources.Database.Medicine.MedicineListFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MedicineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine)
        val fragment = MedicineListFragment.newInstance()
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentHolder, fragment)
        fragmentTransaction.commitNow()

        val addBtn = findViewById<Button>(R.id.addMed)

        addBtn.setOnClickListener {
            val inputName = EditText(this)
            val builderName = AlertDialog.Builder(this)
            builderName.setTitle("Nieuw medicijn toevoegen")
            builderName.setMessage("Voer een naam in voor het nieuwe medicijn:")
            builderName.setPositiveButton(android.R.string.yes) { dialog, which ->
                val name = inputName.text.toString()
                if(!name.isNullOrEmpty()){
                    val input = EditText(this)
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Toevoegen van $name")
                    builder.setMessage("Voeg een beschrijving toe voor $name")
                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                        val medicine = Medicine(name = name, desc = input.text.toString())
                        GlobalScope.launch(Dispatchers.IO) {
                            saveMedicine(medicine)
                        }
                    }
                    builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    }
                    builder.setView(input)
                    builder.show()
                }
            }
            builderName.setNegativeButton(android.R.string.no) { dialog, which ->
            }
            builderName.setView(inputName)
            builderName.show()
        }
    }

    private suspend fun saveMedicine(medicine: Medicine){
        val id = AppDatabase.getDatabase(applicationContext).medicineDao().insert(medicine)
        val intent = Intent(this, MedicineEditActivity::class.java)
        intent.putExtra("medicine", id.toString())
        this.startActivity(intent)
    }
}