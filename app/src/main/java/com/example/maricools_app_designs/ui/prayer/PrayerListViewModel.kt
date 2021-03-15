package com.example.maricools_app_designs.ui.prayer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.maricools_app_designs.utils.models.PrayerModel
import com.example.maricools_app_designs.utils.repositories.PrayerListRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
class PrayerListViewModel
@ViewModelInject
constructor(var repo: PrayerListRepository): ViewModel() {

    val basic: LiveData<List<PrayerModel>> = repo.getBasicPrayer()
    val jesus: LiveData<List<PrayerModel>> = repo.getJesusPrayer()
    val rosary: LiveData<List<PrayerModel>> = repo.getRosaryPrayer()
    val saints: LiveData<List<PrayerModel>> = repo.getSaintsPrayer()
}

