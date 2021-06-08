package com.johnmaricool.mario_designs.utils

import androidx.lifecycle.LiveData
import com.johnmaricool.mario_designs.database.PrayerDao
import com.johnmaricool.mario_designs.utils.models.PrayerFavModel
import com.johnmaricool.mario_designs.utils.models.PrayerModel
import javax.inject.Inject

class PrayerDaoImpl
@Inject constructor(val dao: PrayerDao){

    suspend fun addPrayer(prayer: PrayerModel){
         dao.addPrayer(prayer)
    }

   suspend fun addPrayer(prayer: List<PrayerModel>){
        dao.addPrayer(prayer)
    }

   suspend fun getPrayer(part: String): List<PrayerModel>{
        return dao.getPrayer(part)
    }

     fun getAllPrayers(): List<PrayerModel>{
        return dao.getAllPrayers()
    }

    suspend fun addFavPrayer(prayer: PrayerFavModel){
         dao.addFavPrayer(prayer)
    }

    suspend fun deleteAllPrayers(prayer: List<PrayerModel>){
        dao.deleteAllPrayers(prayer)
    }

    suspend fun removeFavPrayer(prayer: PrayerFavModel){
        dao.removeFavPrayer(prayer)
    }

    suspend fun getAllFavPrayers(): List<PrayerFavModel>{
        return dao.getAllFavPrayers()
    }
}