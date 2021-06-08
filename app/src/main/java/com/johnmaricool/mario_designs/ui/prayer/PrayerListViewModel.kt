package com.johnmaricool.mario_designs.ui.prayer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.johnmaricool.mario_designs.utils.models.PrayerModel
import com.johnmaricool.mario_designs.utils.repositories.PrayerListRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class PrayerListViewModel
@ViewModelInject
constructor(var repo: PrayerListRepository): ViewModel() {

   private val _basic  = MutableLiveData<List<PrayerModel>>()
    private val _jesus  = MutableLiveData<List<PrayerModel>>()
    private val _rosary  = MutableLiveData<List<PrayerModel>>()
    private val _saints  = MutableLiveData<List<PrayerModel>>()
    private val _station  = MutableLiveData<List<PrayerModel>>()

    val basic: LiveData<List<PrayerModel>> = _basic
    val jesus: LiveData<List<PrayerModel>> =  _jesus
    val rosary: LiveData<List<PrayerModel>> =  _rosary
    val saints: LiveData<List<PrayerModel>> = _saints
    val station: LiveData<List<PrayerModel>> = _station

    fun getData(){
        viewModelScope.launch(IO) {
           _jesus.postValue(repo.getJesusPrayer())
           _basic.postValue(repo.getBasicPrayer())
            _rosary.postValue(repo.getRosaryPrayer())
           _saints.postValue(repo.getSaintsPrayer())
            _station.postValue(repo.getStationsOfTheCross())
        }
    }
}

