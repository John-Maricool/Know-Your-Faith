package com.johnmaricool.mario_designs.ui.prayer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.johnmaricool.mario_designs.utils.models.PrayerModel
import com.johnmaricool.mario_designs.utils.repositories.PrayerListRepository

class PrayerListViewModel
@ViewModelInject
constructor(var repo: PrayerListRepository): ViewModel() {

    val basic: LiveData<List<PrayerModel>> = repo.getBasicPrayer()
    val jesus: LiveData<List<PrayerModel>> = repo.getJesusPrayer()
    val rosary: LiveData<List<PrayerModel>> = repo.getRosaryPrayer()
    val saints: LiveData<List<PrayerModel>> = repo.getSaintsPrayer()
    val station: LiveData<List<PrayerModel>> = repo.getStationsOfTheCross()

}

