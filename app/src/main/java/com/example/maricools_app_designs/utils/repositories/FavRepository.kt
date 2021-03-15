package com.example.maricools_app_designs.utils.repositories

import androidx.lifecycle.LiveData
import com.example.maricools_app_designs.utils.models.PrayerFavModel
import com.example.maricools_app_designs.database.PrayerDao

class FavRepository(val prayerDao: PrayerDao) {

    fun getFavPrayers(): LiveData<MutableList<PrayerFavModel>> {
        return prayerDao.getAllFavPrayers()
    }
}