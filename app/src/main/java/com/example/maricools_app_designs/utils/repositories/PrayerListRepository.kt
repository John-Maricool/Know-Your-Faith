package com.example.maricools_app_designs.utils.repositories

import androidx.lifecycle.LiveData
import com.example.maricools_app_designs.CacheMapper
import com.example.maricools_app_designs.database.PrayerDao
import com.example.maricools_app_designs.utils.models.PrayerModel
import com.example.maricools_app_designs.utils.models.PrayerServerModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
}