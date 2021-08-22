package com.example.android.Resources.Database.Medicine

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "medicine", indices = [Index(value = ["name"], unique = true)])
data class Medicine(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var desc: String
){
    constructor() : this(1, "", "")
}


