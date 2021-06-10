package com.johnmaricool.mario_designs.ui.prayer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.johnmaricool.mario_designs.adapters.PrayerViewPagerAdapter
import com.johnmaricool.mario_designs.utils.repositories.PrayerRepository
class PrayerViewModel

@ViewModelInject
constructor(var repository: PrayerRepository) : ViewModel(){

    fun getData(): PrayerViewPagerAdapter{
           return  PrayerViewPagerAdapter(repository.getAllPrayers())

    }
}