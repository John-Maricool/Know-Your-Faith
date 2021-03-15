package com.example.maricools_app_designs.utils.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "order of mass", indices = [Index(value = ["OOMTitle"], unique = true)])
data class OOMModel (@ColumnInfo(name = "OOMTitle") val oomTitle: String,
                @ColumnInfo(name = "OOMContent") val oomContent: String
){
    @PrimaryKey(autoGenerate = true) var uid: Int? = null
}