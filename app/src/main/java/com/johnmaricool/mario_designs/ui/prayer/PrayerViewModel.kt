package com.johnmaricool.mario_designs.ui.prayer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnmaricool.mario_designs.adapters.PrayerViewPagerAdapter
import com.johnmaricool.mario_designs.utils.models.PrayerModel
import com.johnmaricool.mario_designs.utils.repositories.PrayerRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class PrayerViewModel
@ViewModelInject
constructor(var repository: PrayerRepository) : ViewModel(){

    fun getData(): PrayerViewPagerAdapter{
           return  PrayerViewPagerAdapter(repository.getAllPrayers())
    }
}