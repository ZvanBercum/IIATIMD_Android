package com.example.android.Resources.Database.Medicine

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MedicineDao{
    @Query("SELECT * FROM medicine WHERE id = :id LIMIT 1")
    suspend fun findMedicineById(id: Long): Medicine?

    @Query("SELECT * FROM medicine WHERE name = :name LIMIT 1")
    suspend fun findMedicineByName(name: String?): Medicine?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(medicine: Medicine): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg medicines: Medicine)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(medicine: Medicine)

    @Query("DELETE FROM medicine")
    suspend fun deleteAll()

    @Query("DELETE FROM medicine WHERE id = :id")
    suspend fun deleteById(id: Long)

    @get:Query("SELECT * FROM medicine ORDER BY name ASC")
    val allMedicines: LiveData<List<Medicine>>
}