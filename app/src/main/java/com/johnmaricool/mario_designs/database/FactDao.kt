package com.johnmaricool.mario_designs.database

import androidx.room.*
import com.johnmaricool.mario_designs.utils.models.FactModel
import com.johnmaricool.mario_designs.utils.models.FactsFavModel

@Dao
interface FactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFact(fact: List<FactModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFact(fact: FactModel)

    @Query("SELECT * FROM fact WHERE uid = :id")
    suspend fun getFactId(id: Int): FactModel

    @Query("SELECT * FROM fact")
    suspend fun getAllFacts(): List<FactModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavFact(fact: FactsFavModel)

    @Delete
    suspend fun removeFavFact(fact: FactsFavModel)

    @Query("SELECT * FROM fact_fav")
    fun getAllFavFacts(): List<FactsFavModel>
}