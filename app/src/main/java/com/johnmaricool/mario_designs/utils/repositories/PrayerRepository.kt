package com.johnmaricool.mario_designs.utils.repositories

import com.johnmaricool.mario_designs.database.PrayerDao
import com.johnmaricool.mario_designs.utils.models.PrayerModel


class PrayerRepository

constructor(private val prayerDao: PrayerDao){

     fun getAllPrayers(): List<PrayerModel> {
        return prayerDao.getAllPrayers()
    }
}