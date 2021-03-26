package com.example.maricools_app_designs.utils.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName="fact", indices = [Index(value = ["factTitle"], unique = true)])
data class FactModel(
        @ColumnInfo(name = "factTitle") val factTitle: String,
        @ColumnInfo(name = "factContent") val factContent: String
){

    @PrimaryKey(autoGenerate = true) var uid: Int? = null
}
