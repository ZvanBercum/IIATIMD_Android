package com.example.android.Resources.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.android.Resources.Database.Dosage.DosageDao
import com.example.android.Resources.Database.Medicine.Dosage
import com.example.android.Resources.Database.Medicine.Medicine
import com.example.android.Resources.Database.Medicine.MedicineDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Dosage::class, Medicine::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dosageDao(): DosageDao
    abstract fun medicineDao(): MedicineDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "medicine.db"

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                            .addCallback(object : Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    GlobalScope.launch(Dispatchers.IO) { rePopulateDb(INSTANCE) }
                                }
                            }).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}