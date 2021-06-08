package com.johnmaricool.mario_designs.utils.repositories

import com.johnmaricool.mario_designs.utils.PrayerDaoImpl
import com.johnmaricool.mario_designs.utils.models.PrayerFavModel
import com.johnmaricool.mario_designs.utils.models.PrayerModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrayerListRepository
@Inject constructor(var dao: PrayerDaoImpl){

   suspend fun getBasicPrayer(): List<PrayerModel> {
       return dao.getPrayer("Basic")
    }

    suspend fun getJesusPrayer(): List<PrayerModel>{
        return dao.getPrayer("Jesus")
    }

    suspend fun getSaintsPrayer(): List<PrayerModel> {
        return dao.getPrayer("Saints")
    }

   suspend fun getRosaryPrayer(): List<PrayerModel> {
        return dao.getPrayer("Rosary")
    }

   suspend fun getStationsOfTheCross(): List<PrayerModel> {
        return dao.getPrayer("Station")
    }

    suspend fun getFavPrayers(): List<PrayerFavModel> {
        return dao.getAllFavPrayers()
    }
}