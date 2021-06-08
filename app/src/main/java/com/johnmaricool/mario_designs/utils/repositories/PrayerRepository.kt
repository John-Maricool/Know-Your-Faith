package com.johnmaricool.mario_designs.utils.repositories

import com.johnmaricool.mario_designs.utils.PrayerDaoImpl
import com.johnmaricool.mario_designs.utils.models.PrayerModel
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PrayerRepository

@Inject constructor(private val prayerDao: PrayerDaoImpl){

      fun getAllPrayers(): List<PrayerModel> {
        return prayerDao.getAllPrayers()
    }
}