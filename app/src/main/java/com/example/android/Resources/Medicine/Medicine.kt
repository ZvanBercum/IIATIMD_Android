package com.example.android.Resources.Medicine

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Medicine(@PrimaryKey val uuid: Int,
                    @ColumnInfo(name = "name") val name: String?,
                    @ColumnInfo(name = "dosage") val dosage: String?
)