package com.johnmaricool.mario_designs.ui.prayer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.johnmaricool.mario_designs.adapters.PrayerViewPagerAdapter
import com.johnmaricool.mario_designs.utils.repositories.PrayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PrayerViewModel
@Inject constructor(var repository: PrayerRepository) : ViewModel(){

    fun getData(): PrayerViewPagerAdapter{
           return  PrayerViewPagerAdapter(repository.getAllPrayers())

    }
}