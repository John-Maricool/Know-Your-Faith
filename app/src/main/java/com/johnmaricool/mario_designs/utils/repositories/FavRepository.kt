package com.johnmaricool.mario_designs.utils.repositories

import androidx.lifecycle.LiveData
import com.johnmaricool.mario_designs.database.PrayerDao
import com.johnmaricool.mario_designs.utils.models.PrayerFavModel

class FavRepository(val prayerDao: PrayerDao) {

    fun getFavPrayers(): LiveData<MutableList<PrayerFavModel>> {
        return prayerDao.getAllFavPrayers()
    }
}