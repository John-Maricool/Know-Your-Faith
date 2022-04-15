package com.johnmaricool.mario_designs.utils.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="fact_fav")
data class FactsFavModel(
        @PrimaryKey var uid: Int,
        @ColumnInfo(name = "factFavTitle") val factTitle: String,
        @ColumnInfo(name = "factFavContent") val factContent: String
){
}