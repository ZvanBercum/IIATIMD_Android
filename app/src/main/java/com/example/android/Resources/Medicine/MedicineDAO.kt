package com.example.android.Resources.Medicine

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDAO {
    @Query("SELECT * FROM medicine")
    fun getAll(): Flow<List<Medicine>>

    @Query("SELECT * FROM medicine WHERE uuid IN (:medicineIds)")
    fun loadAllByIds(medicineIds: IntArray): List<Medicine>

    @Query("SELECT * FROM medicine WHERE uuid=:id")
    fun loadSingle(id: String): Medicine

    @Insert
    suspend fun insertAll(vararg medicines: Medicine)

    @Insert
    suspend fun insert( medicine: Medicine)

    @Delete
    fun delete(medicine: Medicine)

    @Query("DELETE FROM medicine")
    suspend fun deleteAll()



}