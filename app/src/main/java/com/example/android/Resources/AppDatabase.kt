package com.example.android.Resources

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.Resources.Medicine.Medicine
import com.example.android.Resources.Medicine.MedicineDAO

@Database(entities = [Medicine::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDAO
}
