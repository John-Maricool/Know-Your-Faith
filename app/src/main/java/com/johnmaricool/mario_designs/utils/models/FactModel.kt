package com.johnmaricool.mario_designs.utils.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName="fact", indices = [Index(value = ["factTitle"], unique = true)])
data class FactModel(
        @PrimaryKey var uid: Int,
        @ColumnInfo(name = "factTitle") val factTitle: String,
        @ColumnInfo(name = "factContent") val factContent: String
){
}
