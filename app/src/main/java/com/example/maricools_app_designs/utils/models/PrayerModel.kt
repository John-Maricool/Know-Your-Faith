package com.example.maricools_app_designs.utils.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName="prayer", indices = [Index(value = ["prayerTitle"], unique = true)])
data class PrayerModel(
        @PrimaryKey var uid: Int = 0,
        val prayerTitle: String,
    val prayerContent: String,
   val prayerPart: String
    ){

    }
