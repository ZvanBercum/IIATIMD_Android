package com.example.android.Resources.Medicine

import androidx.annotation.WorkerThread
import com.example.android.Resources.Medicine.Medicine
import com.example.android.Resources.Medicine.MedicineDAO
import kotlinx.coroutines.flow.Flow


class MedicineRepository(private val medicineDAO: MedicineDAO) {

    val allMedicines: Flow<List<Medicine>> = medicineDAO.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(medicine: Medicine) {
        medicineDAO.insert(medicine)
    }
}
