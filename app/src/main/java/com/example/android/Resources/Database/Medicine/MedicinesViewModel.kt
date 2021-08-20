package com.example.android.Resources.Database.Medicine

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.android.Resources.Database.AppDatabase

class MedicinesViewModel(application: Application) : AndroidViewModel(application) {
    private val medicineDao: MedicineDao = AppDatabase.getDatabase(application).medicineDao()
    val medicineList: LiveData<List<Medicine>>

    init {
        medicineList = medicineDao.allMedicines
    }

    suspend fun insert(vararg medicines: Medicine) {
        medicineDao.insert(*medicines)
    }

    suspend fun update(medicine: Medicine) {
        medicineDao.update(medicine)
    }

    suspend fun deleteAll() {
        medicineDao.deleteAll()
    }
}