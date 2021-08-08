package com.example.android.Resources

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.android.Resources.Medicine.Medicine
import com.example.android.Resources.Medicine.MedicineDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Medicine::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDAO

    private class MedicineDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.medicineDao())
                }
            }
        }

        suspend fun populateDatabase(medicineDao: MedicineDAO) {
            // Delete all content here.
            medicineDao.deleteAll()

            // Add sample words.
            val medicine = Medicine(3, "test", "test")
            medicineDao.insert(medicine)

        }

    }

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).addCallback(MedicineDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}
