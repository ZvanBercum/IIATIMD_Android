package com.example.android.Resources.Database.Medicine

import androidx.room.*

@Entity(tableName = "dosage",
    foreignKeys = [ForeignKey(entity = Medicine::class, parentColumns = ["id"], childColumns = ["medicineId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("medicineId")])
data class Dosage(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0,
    @ColumnInfo(name = "medicineId") var medicineId: Long,
    @ColumnInfo(name = "dosage") var dosage: String,
    @ColumnInfo(name = "date") var date: Long
){
    constructor() : this(0L, 0L, "", 0L)
}


