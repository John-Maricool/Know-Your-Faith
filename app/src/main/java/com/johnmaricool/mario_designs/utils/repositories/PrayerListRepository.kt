package com.johnmaricool.mario_designs.utils.repositories

import androidx.lifecycle.LiveData
import com.johnmaricool.mario_designs.database.PrayerDao
import com.johnmaricool.mario_designs.utils.models.PrayerModel

class PrayerListRepository
constructor(var dao: PrayerDao){

    fun getBasicPrayer(): LiveData<List<PrayerModel>> {
       return dao.getPrayer("Basic")
    }

    fun getJesusPrayer(): LiveData<List<PrayerModel>> {
        return dao.getPrayer("Jesus")
    }

    fun getSaintsPrayer(): LiveData<List<PrayerModel>> {
        return dao.getPrayer("Saints")
    }

    fun getRosaryPrayer(): LiveData<List<PrayerModel>> {
        return dao.getPrayer("Rosary")
    }

    fun getStationsOfTheCross(): LiveData<List<PrayerModel>> {
        return dao.getPrayer("Station")
    }
}