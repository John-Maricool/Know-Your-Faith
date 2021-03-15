package com.example.maricools_app_designs.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.maricools_app_designs.utils.models.PrayerFavModel
import com.example.maricools_app_designs.utils.models.PrayerModel

@Dao
interface PrayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPrayer(prayer: PrayerModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun addPrayer(prayer: List<PrayerModel>)

    @Query("SELECT * FROM prayer WHERE prayerPart = :part")
     fun getPrayer(part: String): LiveData<List<PrayerModel>>

    @Query("SELECT * FROM prayer")
    fun getAllPrayers(): List<PrayerModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavPrayer(prayer: PrayerFavModel)

    @Delete
     fun deleteAllPrayers(prayer: List<PrayerModel>)

    @Delete
    suspend fun removeFavPrayer(prayer: PrayerFavModel)

    @Query("SELECT * FROM prayer_fav")
    fun getAllFavPrayers(): LiveData<MutableList<PrayerFavModel>>
}

