package com.example.android

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.Resources.Adapters.MedicineAdapter
import com.example.android.Resources.Medicine.*

class MedicineActivity : AppCompatActivity() {
    private lateinit var repository : MedicineRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine)
        val medicines = repository.allMedicines.asLiveData()
    }
}