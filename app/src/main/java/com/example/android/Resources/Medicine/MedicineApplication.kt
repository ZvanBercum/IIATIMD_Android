package com.example.android.Resources.Medicine

import android.app.Application
import com.example.android.Resources.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MedicineApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    //Use Lazy loading
    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { MedicineRepository(database.medicineDao()) }
}
