package com.example.maricools_app_designs.utils.repositories

import com.example.maricools_app_designs.database.PrayerDao
import com.example.maricools_app_designs.utils.models.PrayerModel
import kotlinx.coroutines.CoroutineScope


class PrayerRepository

constructor(private val prayerDao: PrayerDao){

     fun getAllPrayers(): List<PrayerModel> {
        return prayerDao.getAllPrayers()
    }
}