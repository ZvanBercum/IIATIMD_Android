package com.example.android.Resources.Database.Dosage

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.Resources.Database.Medicine.Dosage
import com.example.android.Resources.Database.Medicine.Medicine

@Dao
interface DosageDao{
    @Query("SELECT * FROM dosage WHERE id = :id LIMIT 1")
    suspend fun findDosageById(id: Long): Dosage?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg dosages: Dosage)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(dosage: Dosage)

    @Query("DELETE FROM dosage")
    suspend fun deleteAll()

    @get:Query("SELECT * FROM dosage ORDER BY medicineId ASC")
    val allDosages: LiveData<List<Dosage>>
}