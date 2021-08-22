package com.example.android.Resources.Database

import com.example.android.Resources.Database.Dosage.DosageDao
import com.example.android.Resources.Database.Medicine.Dosage
import com.example.android.Resources.Database.Medicine.Medicine
import com.example.android.Resources.Database.Medicine.MedicineDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun rePopulateDb(database: AppDatabase?) {
    database?.let { db ->
        withContext(Dispatchers.IO) {
            val medicineDao: MedicineDao = db.medicineDao()
            val dosageDao: DosageDao = db.dosageDao()

            dosageDao.deleteAll()
            medicineDao.deleteAll()

            val medicineOne = Medicine(name = "Ibuprofen", desc = "Tegen hoge koorts en hevige pijn")
            val medicineTwo = Medicine(name = "Paracetamol", desc = "Tegen koorts en pijn")
            val medTwoId = medicineDao.insert(medicineTwo)

            val dosageOne = Dosage(dosage = "250mg", date = 1632228011, medicineId = medicineDao.insert(medicineOne))
            val dosageTwo = Dosage(dosage = "500mg", date = 1632228012, medicineId = medTwoId)
            val dosageThree = Dosage(dosage = "500mg", date = 1632573611, medicineId = medTwoId)
            val dosageFour = Dosage(dosage = "100mg", date = 1632660011, medicineId = medTwoId)
            val dosageFive = Dosage(dosage = "3500mg", date = 1632746411, medicineId = medTwoId)

            dosageDao.insert(dosageOne)
            dosageDao.insert(dosageTwo)
            dosageDao.insert(dosageThree)
            dosageDao.insert(dosageFour)
            dosageDao.insert(dosageFive)

        }
    }
}