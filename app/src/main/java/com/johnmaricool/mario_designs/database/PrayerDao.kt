package com.johnmaricool.mario_designs.database

import androidx.room.*
import com.johnmaricool.mario_designs.utils.models.PrayerFavModel
import com.johnmaricool.mario_designs.utils.models.PrayerModel

@Dao
interface PrayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPrayer(prayer: PrayerModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun addPrayer(prayer: List<PrayerModel>)

    @Query("SELECT * FROM prayer WHERE prayerPart = :part")
     suspend fun getPrayer(part: String): List<PrayerModel>

    @Query("SELECT * FROM prayer")
     fun getAllPrayers(): List<PrayerModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavPrayer(prayer: PrayerFavModel)

    @Delete
     suspend fun deleteAllPrayers(prayer: List<PrayerModel>)

    @Delete
    suspend fun removeFavPrayer(prayer: PrayerFavModel)

    @Query("SELECT * FROM prayer_fav")
    suspend fun getAllFavPrayers(): List<PrayerFavModel>
}

