package com.example.maricools_app_designs.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.maricools_app_designs.utils.models.OOMModel

@Dao
interface OOMDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun addOOM(oom: List<OOMModel>)

    @Query("SELECT * FROM `order of mass` WHERE OOMTitle = :title")
    fun getAllOrderTitles(title: String): OOMModel
}