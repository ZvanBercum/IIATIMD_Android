package com.example.android.Resources.Database.Dosage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.android.Resources.Database.AppDatabase
import com.example.android.Resources.Database.Medicine.Dosage
import com.example.android.Resources.Database.Medicine.Medicine
import com.example.android.Resources.Database.Medicine.MedicineDao

class DosageViewModel(application: Application) :AndroidViewModel(application){
    private val dosageDao: DosageDao = AppDatabase.getDatabase(application).dosageDao()
    private val medicineDao: MedicineDao = AppDatabase.getDatabase(application).medicineDao()

    val dosageList: LiveData<List<Dosage>>
    val medicineList: LiveData<List<Medicine>>

    init{
        dosageList = dosageDao.allDosages
        medicineList = medicineDao.allMedicines
    }

    suspend fun insert(vararg dosages: Dosage) {
        dosageDao.insert(*dosages)
    }

    suspend fun update(dosage: Dosage) {
        dosageDao.update(dosage)
    }

    suspend fun deleteAll() {
        dosageDao.deleteAll()
    }
}