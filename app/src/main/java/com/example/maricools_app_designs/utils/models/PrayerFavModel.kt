package com.example.maricools_app_designs.utils.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="prayer_fav")
data class PrayerFavModel(
        @PrimaryKey var uid: Int,
    @ColumnInfo(name = "prayerFavTitle") val prayerTitle: String,
    @ColumnInfo(name = "prayerFavContent") val prayerContent: String
    ){
    }
