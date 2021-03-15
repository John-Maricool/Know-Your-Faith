package com.example.maricools_app_designs.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.maricools_app_designs.utils.models.FactModel
import com.example.maricools_app_designs.utils.models.FactsFavModel
import com.example.maricools_app_designs.utils.models.PrayerFavModel
import com.example.maricools_app_designs.utils.models.PrayerModel

@Dao
interface FactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun addFact(fact: List<FactModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFact(fact: FactModel)

    @Query("SELECT * FROM fact WHERE factPart = :part")
     fun getAllFacts(part: String): List<FactModel>

    @Query("SELECT * FROM fact WHERE uid = :id")
    fun getFactId(id: Int): FactModel

    @Query("SELECT * FROM fact")
    fun getAllFacts(): List<FactModel>

    @Delete
    fun deleteAllFacts(fact: List<FactModel>)

    @Query("SELECT * FROM fact")
    fun getAllFact(): MutableList<FactModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavFact(fact: FactsFavModel)

    @Delete
    suspend fun removeFavFact(fact: FactsFavModel)

    @Query("SELECT * FROM fact_fav")
    fun getAllFavFacts(): LiveData<MutableList<FactsFavModel>>
}